package com.ssafy.b105.service.blockchain;

import com.ssafy.b105.dto.blockchain.NewWalletDto;
import com.ssafy.b105.entity.User;
import com.ssafy.b105.entity.blockchain.Transaction;
import com.ssafy.b105.entity.blockchain.Wallet;
import com.ssafy.b105.repository.TransactionRepository;
import com.ssafy.b105.repository.blockchain.WalletRepository;
import com.ssafy.b105.utils.BalanceConverter;
import com.ssafy.b105.utils.BlockchainConnector;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.token.Token;

@Service
public class WalletServiceImpl implements WalletService {

  private final BlockchainConnector connector;
  private final WalletRepository walletRepository;
  private final TransactionRepository transactionRepository;

  private Token tokenMgr;

  private BigInteger decimals;

  public WalletServiceImpl(BlockchainConnector connector,
      WalletRepository walletRepository,
      TransactionRepository transactionRepository) {
    this.connector = connector;
    this.walletRepository = walletRepository;
    this.tokenMgr = connector.getTokenMgr();
    this.decimals = connector.getDecimals();
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Optional<Wallet> createAccount(User user) {
    try {
      NewWalletDto newWalletDto = connector.createAccount();
      Wallet wallet = Wallet.of(user, newWalletDto);
      walletRepository.save(wallet);
      return Optional.ofNullable(wallet);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.ofNullable(null);
  }

  @Override
  public String findAccountByUser(User user) {
    Wallet wallet = findByUser(user);
    return wallet.getAccount();
  }

  @Override
  public Long findBalanceByUser(User user) throws ExecutionException, InterruptedException {
    Wallet wallet = findByUser(user);
    BigInteger balance = tokenMgr.balanceOf(wallet.getAccount()).sendAsync().get();
    return BalanceConverter.bigIntegerToLong(balance, decimals);
  }

  @Override
  public Long chargeToken(User user, Long amount)
      throws ExecutionException, InterruptedException {
    Wallet wallet = findByUser(user);
    TransactionReceipt receipt = tokenMgr.chargeToken(
            wallet.getAccount(),
            BalanceConverter.longToBigInteger(amount, decimals))
        .sendAsync().get();
    Transaction save = transactionRepository.save(Transaction.from(receipt));
    return 0L;
  }

  private Wallet findByUser(User user) {
    return walletRepository.findWalletByUser(user)
        .orElseThrow(() -> new IllegalArgumentException());
  }


}
