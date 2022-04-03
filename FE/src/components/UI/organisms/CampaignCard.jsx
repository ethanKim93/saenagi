import { useState } from 'react';
import Dday from '../molecules/D-Day';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import UserButton from '../molecules/UserButton';
import ProgressBar from '../molecules/ProgressBar';
import styled from '@emotion/styled';
import Text from '../atoms/Text';
import { Grid } from '@mui/material';
import WithdrawModal from './Modal/WithdrawModal';
import DonateModal from '../organisms/Modal/DonateModal';

function CampaignCard(props) {
  const [isOpen, setIsOpen] = useState(false);
  const openWithdrawModal = () => {};
  const {
    id,
    isEnd,
    shelterName,
    targetDonation,
    contentImageUrl,
    title,
    type,
    lastModifiedDate,
    endDate,
    hashtags,
    balance,
    dday,
  } = props;

  const targetMoney = targetDonation ? targetDonation.toLocaleString() : targetDonation;

  return (
    //max min 똑같은 이유, ProgressBar에 영향을 안주기위해 고정값으로 주려고..
    <StyledCard>
      <CardContent>
        <Grid container>
          <Grid item xs={9}>
            <StyledText className="body1" text={`${targetMoney}` + '원'} />
          </Grid>
          <StyledGrid item xs={3}>
            <Dday dday={dday} />
          </StyledGrid>
        </Grid>
        {/* <Typography variant="h5" component="div">
          100,100,000 원
        </Typography> */}
        <ProgressBar percent="0.5" width="321" />
        <RightContainer>
          <Typography
            sx={{ fontSize: 16 }}
            style={{ marginTop: '10px' }}
            color="text.secondary"
            gutterBottom
          >
            {balance}원
          </Typography>
          <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
            {lastModifiedDate} ~ {endDate}까지
          </Typography>
        </RightContainer>
        {/* <UserButton
          type="submit"
          fullWidth
          variant="contained"
          text="캠페인 기부하기"
          size="large"
          func={() => setIsOpen(true)}
        />
        <Typography sx={{ fontSize: 14 }}>모금단체</Typography>
        <Typography>마석유기견보호소</Typography>
      /> */}
        <WithdrawModal isOpen={isOpen} />
        <DonateModal />
        <Typography sx={{ fontSize: 14 }}>모금단체</Typography>
        <Typography>{shelterName}</Typography>
      </CardContent>
    </StyledCard>
  );
}

const RightContainer = styled.div`
  width: 100%;
  display: block;
  text-align: right;
`;

const MediaDiv = styled.div`
  @media screen and (max-width: 900px) {
    display: None;
  }
`;

const StyledText = styled(Text)`
  text-align: center;
  justify-content: center;
  margin: 0;
  font-size: 1.2rem;
`;

const StyledGrid = styled(Grid)`
  display: inline-grid;
  grid-template-columns: 100%;
`;

const StyledCard = styled(Card)`
  position: sticky;
  top: 50px;
`;

export default CampaignCard;
