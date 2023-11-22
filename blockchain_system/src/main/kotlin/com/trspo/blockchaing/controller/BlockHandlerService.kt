package com.trspo.blockchaing.controller

import com.google.protobuf.Empty
import com.trspo.blockchaing.entity.Block
import com.trspo.blockchaing.repo.BlockChainRepository
import com.trspo.blockchaing.services.MessageProducer
import com.trspo.grpc.block.AddBlockRequest
import com.trspo.grpc.block.BlockServiceGrpc
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

@GrpcService
class BlockHandlerService : BlockServiceGrpc.BlockServiceImplBase() {
    @Autowired
    lateinit var repository: BlockChainRepository

    @Autowired
    lateinit var messageProducer: MessageProducer

    val logger: Logger = LoggerFactory.getLogger(BlockHandlerService::class.java)

    override fun addMinedBlock(request: AddBlockRequest, responseObserver: StreamObserver<Empty>) {
        val minedBlock: Block = Block.fromRequestToBlock(request)
        logger.info(String.format("Get a new mined block with %s hash"),minedBlock.hash)
        repository.save(minedBlock)
        messageProducer.sendStartMessage()
    }

}