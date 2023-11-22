package com.trspo.blockchaing.repo

import com.trspo.blockchaing.entity.Block
import org.springframework.data.mongodb.repository.MongoRepository

interface BlockChainRepository:MongoRepository<Block,String> {

}