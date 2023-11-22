# PoW consensus mechanism - Distributed Systems, Fall 2020

## Overview

This project is a microservice architecture of a blockchain system prototype with a Proof of Work (PoW) consensus mechanism implemented in Kotlin
for Distributed Systems Course, Fall 2020. 

Proof of Work is a consensus algorithm that ensures the security and integrity of a blockchain by requiring participants to demonstrate a computational effort to add a new block to the chain.

## Background

Proof of Work is a mechanism that requires participants of a blockchain network, known as miners, to solve complex mathematical problems
in order to add a new block of transactions to the blockchain.

## Structure
The architecture consists of 4 key components:
1. Transaction pool - a service that generates random transactions that need to be added to the blockchain
2. Node - an abstraction of a blockchain node, that:
    1. Pulls transactions from a transaction pool and forms a block
    2. Solves a computational puzzle in order to mine a block
    3. Sends a mined block to the network
    4. Validates blocks mined by other nodes
3. Blockchain System - a service that represents the blockchain

## Communication
1. Nodes are:
    1. RabbitMQ producers: publish a message about the newly mined blocks.
    2. A gRPC client to directly call a method of the transaction pool to get published transactions to form a block
2. Transaction Pool is a gRPC server being called by nodes retrieving new transaction
3. Blockchain System is a RabbitMQ consumer getting the mined block after the network has validated it 
