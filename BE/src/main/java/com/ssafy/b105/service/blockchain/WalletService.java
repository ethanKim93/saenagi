package com.ssafy.b105.service.blockchain;

import com.ssafy.b105.dto.blockchain.ChargeDto;
import com.ssafy.b105.entity.User;
import com.ssafy.b105.entity.blockchain.Wallet;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface WalletService {

  Optional<Wallet> createAccount(User user);

  String findAccountByUser(User user);

  Long findBalanceByUser(User user) throws ExecutionException, InterruptedException;

  Long chargeToken(User user, Long amount)
      throws ExecutionException, InterruptedException;

}
