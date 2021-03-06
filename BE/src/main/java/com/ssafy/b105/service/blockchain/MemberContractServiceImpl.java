package com.ssafy.b105.service.blockchain;

import com.ssafy.b105.entity.blockchain.wrapper.member.Member;
import com.ssafy.b105.entity.common.MemberType;
import com.ssafy.b105.utils.BlockchainConnector;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Service
public class MemberContractServiceImpl implements
    MemberContractService {

  private final BlockchainConnector connector;

  private Member memberMgr;

  public MemberContractServiceImpl(BlockchainConnector connector) {
    this.connector = connector;
    this.memberMgr = connector.getMemberMgr();
  }

  @Override
  public boolean registMember(String account, MemberType type) {
    try {
<<<<<<< HEAD
      TransactionReceipt receipt = memberMgr.newMember(account, type.name())
          .sendAsync().get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
=======
      TransactionReceipt receipt = memberMgr.newMember(account, type.toString()).send();
      return true;
    } catch (Exception e) {
>>>>>>> dev
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean isTransferPossible(String from, String to) {
    try {
<<<<<<< HEAD
      return memberMgr.isTransferPossible(from,to).sendAsync().get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
=======
      return memberMgr.isTransferPossible(from, to).send();
    } catch (Exception e) {
>>>>>>> dev
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean isMember(String account) {
    try {
<<<<<<< HEAD
      return memberMgr.isMember(account).sendAsync().get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
=======
      return memberMgr.isMember(account).send();
    } catch (Exception e) {
>>>>>>> dev
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean isWithdrawalPossible(String account) {
    try {
<<<<<<< HEAD
      return memberMgr.isWithdrawalPossible(account).sendAsync().get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
=======
      return memberMgr.isWithdrawalPossible(account).send();
    } catch (Exception e) {
>>>>>>> dev
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean isSupporter(String account) {
    try {
<<<<<<< HEAD
      return memberMgr.isSupporter(account).sendAsync().get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
=======
      return memberMgr.isSupporter(account).send();
    } catch (Exception e) {
>>>>>>> dev
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean isShelter(String account) {
    try {
<<<<<<< HEAD
      return memberMgr.isShelter(account).sendAsync().get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
=======
      return memberMgr.isShelter(account).send();
    } catch (Exception e) {
>>>>>>> dev
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean isProjectCampaign(String account) {
    try {
<<<<<<< HEAD
      return memberMgr.isPrjoectCampaign(account).sendAsync().get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
=======
      return memberMgr.isPrjoectCampaign(account).send();
    } catch (Exception e) {
>>>>>>> dev
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean isShelterCampaign(String account) {
    try {
<<<<<<< HEAD
      return memberMgr.isShelterCampaign(account).sendAsync().get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
=======
      return memberMgr.isShelterCampaign(account).send();
    } catch (Exception e) {
>>>>>>> dev
      e.printStackTrace();
    }
    return false;
  }
}
