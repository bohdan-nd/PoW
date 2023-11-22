package com.trspo.node.services

import com.google.common.hash.Hashing
import com.trspo.node.entities.Block
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

@Service
class ValidationService {
    fun validate(block: Block): Boolean {
        val blockHeader = block.getBlockHeader()
        val nonce: Long = block.nonce

        val input = blockHeader + nonce
        val hash = getHash(input)
        val target = stringMultiply("0", block.accuracy)

        return checkHash(hash, target)
    }

    private fun checkHash(hash: String, target: String): Boolean {
        return hash.startsWith(target)
    }

    private fun getHash(text: String): String {
        return Hashing.sha256()
                .hashString(text, StandardCharsets.UTF_8)
                .toString()
    }

    private fun stringMultiply(text: String, times: Int): String {
        return text.repeat(times)
    }
}