package com.trspo.node.services

import com.trspo.grpc.block.AddBlockRequest
import com.trspo.grpc.block.BlockServiceGrpc.BlockServiceBlockingStub
import com.trspo.grpc.block.BlockServiceGrpc
import com.trspo.node.entities.Block
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.stereotype.Service

@Service
class BlockService : BlockServiceGrpc.BlockServiceImplBase() {
    private val url: String = "system-pow"
    private val channel: ManagedChannel = ManagedChannelBuilder.forAddress(url, 9090)
            .usePlaintext()
            .build()
    private val stub: BlockServiceBlockingStub = BlockServiceGrpc.newBlockingStub(channel)

    fun sendMinedBlockToStorage(block: Block) {
        val blockMessage = Block.toBlockMessage(block)
        val addBlockRequest = AddBlockRequest.newBuilder()
                .setMinedBlock(blockMessage)
                .build()

        stub.addMinedBlock(addBlockRequest)
    }
}