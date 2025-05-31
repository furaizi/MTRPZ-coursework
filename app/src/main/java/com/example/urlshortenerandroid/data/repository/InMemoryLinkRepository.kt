package com.example.urlshortenerandroid.data.repository

import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import kotlinx.coroutines.delay
import retrofit2.Response
import java.security.SecureRandom
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.asKotlinRandom

class InMemoryLinkRepository : LinkRepository {

    /** Store of links: shortCode → LinkResponse */
    private val links = ConcurrentHashMap<String, LinkResponse>()

    /** Statistics: shortCode → LinkStatistics */
    private val stats = ConcurrentHashMap<String, LinkStatistics>()

    /** Random code generator (8 characters: a-z0-9) */
    private val rnd = SecureRandom().asKotlinRandom()

    /** Simulate network delay */
    private suspend fun networkDelay() = delay(250)

    /** Generate short code */
    private fun genShortCode(): String =
        (1..8).joinToString("") { "abcdefghijklmnopqrstuvwxyz0123456789".random(rnd).toString() }

    override suspend fun create(url: String): Result<LinkResponse> = runCatching {
        networkDelay()
        val code = genShortCode()
        val now = LocalDateTime.now()
        val link = LinkResponse(
            shortCode = code,
            url = "https://short.url/$code",
            originalUrl = url,
            expiresAt = null,          // does not expire
            createdAt = now
        )
        links[code] = link
        stats[code] = LinkStatistics(code, clicks = 0, uniqueVisitors = 0, lastAccessedAt = null, isActive = true)
        link
    }

    override suspend fun details(id: String): Result<LinkResponse> = runCatching {
        networkDelay()
        links[id] ?: error("Ссылка $id не найдена")
    }

    override suspend fun stats(id: String): Result<LinkStatistics> = runCatching {
        networkDelay()
        stats[id] ?: error("Статистика $id не найдена")
    }

    override suspend fun delete(id: String): Result<Response<Unit>> = runCatching {
        networkDelay()
        val removed = links.remove(id)
        val st = stats[id]
        if (removed == null || st == null) error("Ссылка $id не найдена")
        stats[id] = st.copy(isActive = false)
        Response.success(Unit)        // simulate HTTP 204
    }

    /* ---------- additional util methods for UI tests ---------- */

    /** Increment click count and, optionally, unique visitors. */
    fun click(code: String, isUniqueVisitor: Boolean = true) {
        stats.computeIfPresent(code) { _, old ->
            old.copy(
                clicks = old.clicks + 1,
                uniqueVisitors = old.uniqueVisitors + if (isUniqueVisitor) 1 else 0,
                lastAccessedAt = LocalDateTime.now()
            )
        }
    }

    /** Get all stored links — useful for debug screens. */
    fun dumpAllLinks(): List<LinkResponse> = links.values.toList()
}
