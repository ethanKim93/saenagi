package com.ssafy.b105.service;

import com.ssafy.b105.dto.UserDonateDto;
import com.ssafy.b105.entity.user.Authority;
import com.ssafy.b105.entity.user.User;
import com.ssafy.b105.exception.DuplicateException;
import com.ssafy.b105.exception.ExpressionValidateException;
import com.ssafy.b105.repository.AuthorityRepository;
import com.ssafy.b105.repository.CampaignRepository;
import com.ssafy.b105.repository.SupportLogRepository;
import com.ssafy.b105.repository.UserRepository;
import com.ssafy.b105.service.blockchain.MemberContractService;
import com.ssafy.b105.service.blockchain.WalletService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final CampaignRepository campaignRepository;
  private final SupportLogRepository supportLogRepository;

  private final AuthorityRepository authorityRepository;
  private final PasswordEncoder passwordEncoder;
  private final WalletService walletService;
  private final MemberContractService memberContractService;

  @Override
  public boolean duplicatePrincipalCheck(String principal) {
    //아이디 중복 검증
    if (userRepository.findOneByPrincipal(principal).orElse(null) != null) {
      throw new DuplicateException("이미 가입되어 있는 아이디 입니다.");
    }
    return true;
  }

  @Override
  public boolean duplicateNameCheck(String name) {
    //닉네임 중복 검증

    if (userRepository.findOneByName(name).orElse(null) != null) {
      throw new DuplicateException("이미 사용중인 이름 입니다.");
    }
    return true;
  }


  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     Optional<User> user = this.userRepository.findByPrincipal(username);

    /**
     * Username 값이 DATA DB 에 존재하지 않는 경우
     * UsernameNotFoundException 에러 메소드를 사용합니다.
     * */
    if (user.isPresent()) {
      return org.springframework.security.core.userdetails.User.builder()
        .username(user.get().getPrincipal())
        .password(user.get().getCredential())
        .roles(user.get().getAuthorities().toArray(String[]::new))
        .build();
    } else {
      throw new UsernameNotFoundException(username + "정보를 찾을 수 없습니다.");
    }
  }

  @Override
  public User saveOrUpdateUser(User user) throws ChangeSetPersister.NotFoundException {
    isValied(user);
//    Wallet wallet = walletService.createAccount(user).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
//    if(user.getRole().equals("SUPPORTER")){
//      memberContractService.registMember(wallet.getAccount(), MemberType.Supporter);
//    }
//    else{
//      memberContractService.registMember(wallet.getAccount(), MemberType.Shelter);
//    }

    user.encodePassword(this.passwordEncoder);
    return this.userRepository.save(user);
  }

  @Override
  @Transactional
  public User login(String prinripal, String credential) {
    User user = userRepository.findByPrincipal(prinripal).orElseThrow();
    return user;
  }

  @Override
  public Authority getById(long id) throws ChangeSetPersister.NotFoundException {
    Authority authority = authorityRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    return authority;
  }

  @Override
  public void setAuthority(User user, Authority authority) {
    authority.setMember(user);
    authorityRepository.save(authority);
  }

  //유효성 검증
  private void isValied(User user) {
    //아이디 정규식 검증 > Dto 에서 따로 처리할것
    if (user.getPrincipal().isBlank()) throw new NullPointerException("아이디를 입력하십시오.");
    else if(!Pattern.matches("([\\w-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$",user.getPrincipal().trim())){
      throw new ExpressionValidateException("아이디 형식이 올바르지 않습니다.");
    }
    //비밀번호 정규식 검증
    if (user.getCredential().isBlank()) throw new NullPointerException("비밀 번호를 입력하십시오.");
    else if(!Pattern.matches("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$",user.getCredential().trim())){
      throw new ExpressionValidateException("비밀번호 형식이 올바르지 않습니다.");
    }
    //닉네임 정규식 검증
    if (user.getName().isBlank()) throw new NullPointerException("닉네임을 입력하십시오.");
    else if(!Pattern.matches("^[가-힣a-zA-Z]+$",user.getName().trim())){
      throw new ExpressionValidateException("닉네임 형식이 올바르지 않습니다.");
    }
    //아이디 중복 검증
    if (userRepository.findOneByPrincipal(user.getPrincipal()).orElse(null) != null) {
      throw new DuplicateException("이미 가입되어 있는 아이디 입니다.");
    }
    //닉네임 중복 검증
    if(userRepository.findOneByName(user.getName()).orElse(null) != null){
      throw new DuplicateException("이미 사용중인 이름 입니다.");
    }
  }

}
