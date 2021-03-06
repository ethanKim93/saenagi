import { useState } from 'react';
import { Box, Modal } from '@mui/material/';
import Toast from '../../atoms/SweetAlert';
import UserButton from '../../molecules/UserButton';
import UserCancelButton from '../../molecules/UserCancelButton';
import Spinner from '../Spinner';
import styled from '@emotion/styled';
import PaymentAPI from '../../../../api/paymentAPI';
import { useParams } from 'react-router-dom';
import SpinnerCSS from '../../organisms/Spinner.css';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  pt: 2,
  px: 4,
  pb: 3,
};

function ChildModal(props) {
  const { value } = props;
  const { campaignId } = useParams();
  // 문자열에서 , 빼기
  const values = Number(value.split(',').join(''));
  //spinner
  const [loading, setLoading] = useState(false);

  const [open, setOpen] = useState(false);
  const handleOpen = () => {
    setOpen(true);
    console.log(campaignId);
  };
  const handleClose = () => {
    setOpen(false);
  };

  // 켐페인 후원 api
  const donationToShelter = async (event) => {
    event.preventDefault();
    const donationData = {
      campaignId: Number(campaignId),
      amount: Number(values),
    };
    console.log(donationData);
    setLoading(true);
    await PaymentAPI.donation(donationData)
      .then((response) => {
        console.log(response);
        setLoading(false);
        window.location.reload(true);
        Toast.fire({
          icon: 'success',
          title: '기부에 성공했습니다!',
        });
      })
      .catch((error) => {
        console.log(error);
        setLoading(false);
        window.location.reload(true);
        // error 인데 실질적으로 기부는 되는 상황 Toast만 변경해서 성공한 것 처럼 할 수는 있음
        Toast.fire({
          icon: 'error',
          title: '기부 도중 에러가 발생하여 기부에 실패했습니다 :(',
        });
      });
  };
  // Number(value).toLocaleString()

  return (
    <div>
      <div className={loading ? 'parentDisable' : ''} width="100%"></div>
      <UserButton
        func={handleOpen}
        type="submit"
        variant="contained"
        text="기부하기"
        size="large"
      />
      <Modal
        hideBackdrop
        open={open}
        onClose={handleClose}
        aria-labelledby="child-modal-title"
        aria-describedby="child-modal-description"
      >
        <Box sx={{ ...style, width: 300 }}>
          <p id="child-modal-description" align="center">
            최종 결제 금액은 {value}원 입니다. <br /> 결제하시겠습니까?
          </p>
          <Buttonblock>
            <UserCancelButton
              func={handleClose}
              type="submit"
              variant="contained"
              text="취소하기"
              size="large"
            />
            <UserButton
              type="submit"
              variant="contained"
              text="기부하기"
              size="large"
              func={donationToShelter}
            />
          </Buttonblock>
          {loading && <Spinner loading={loading} />}
        </Box>
      </Modal>
    </div>
  );
}

function DonateModal(props) {
  const { title, shelterName, thumbnailImageUrl } = props;

  const [open, setOpen] = useState(false);
  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
    setValues(0);
  };
  const [values, setValues] = useState('');

  const handleChange = (e) => {
    //value의 값이 숫자가 아닐경우 빈문자열로 replace 해버림.
    let onlyNumber = e.currentTarget.value;
    onlyNumber = Number(onlyNumber.replaceAll(',', '').replace(/[^0-9]/g, ''));
    const formatValue = onlyNumber.toLocaleString('ko-KR');
    setValues(formatValue);
  };

  return (
    <>
      <UserButton
        fullWidth
        func={handleOpen}
        type="submit"
        variant="contained"
        text="기부하기"
        size="large"
      />
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="parent-modal-title"
        aria-describedby="parent-modal-description"
      >
        <Box sx={{ ...style, width: 400 }}>
          <Title id="parent-modal-title">결제하기</Title>
          <DivContainer>
            <ImgDiv>
              <img
                src={`https://senagi.site/api/imgs/${thumbnailImageUrl}`}
                alt="이미지없음"
                objectfit="cover"
                width="111px"
                height="97px"
                style={{ borderRadius: '5px' }}
              />
            </ImgDiv>
            <DivColumn>
              <P1 className="body3"> {shelterName} </P1>
              <P2 id="parent-modal-description">{title}</P2>
            </DivColumn>
          </DivContainer>
          <Input1
            type="text"
            name="dotionForm"
            value={values}
            placeholder="금액을 입력하세요 "
            onChange={handleChange}
            autoFocus
            autoComplete="off"
          />
          <Buttonblock>
            <UserCancelButton
              func={handleClose}
              type="submit"
              variant="contained"
              text="취소하기"
              size="large"
            />
            <ChildModal value={values} />
          </Buttonblock>
        </Box>
      </Modal>
    </>
  );
}

export default DonateModal;

const Title = styled.h2`
  text-align: center;
`;

const DivContainer = styled.div`
  display: flex;
  justify-content: flex-start;
`;

const DivColumn = styled.div`
  display: flex;
  flex-direction: column;
`;

const ImgDiv = styled.div`
  margin-right: 10px;
  width: 111px;
  height: 97px;
`;

const P1 = styled.p`
  margin-top: 0;
`;

const P2 = styled.p`
  margin-top: 5px;
`;

const Input1 = styled.input`
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  color: black;
  margin-top: 15px;
  margin-bottom: 15px;
  border: none;
  border-bottom: 1px solid black;
  outline: none;
  background: transparent;
  text-align: right;
`;

const LineDiv = styled.div`
  display: inline-block;
`;

const Buttonblock = styled.div`
  display: flex;
  justify-content: center;
`;
