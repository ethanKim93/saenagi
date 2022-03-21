import { css } from '@emotion/react';

const commonStyles = css`
  @font-face {
    font-family: 'GM';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
      format('woff');
    font-weight: normal;
    font-style: normal;
  }
  @font-face {
    font-family: 'GB';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansBold.woff')
      format('woff');
    font-weight: normal;
    font-style: normal;
  }
  @font-face {
    font-family: 'GL';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansLight.woff')
      format('woff');
    font-weight: normal;
    font-style: normal;
  }
  body {
    font-family: 'GM';
  }
  .header1 {
    font-style: normal;
    font-weight: 700;
    font-size: 34px;
    line-height: 39px;
    letter-spacing: -0.02em;
    font-family: 'GB';
  }
  .header2 {
    font-style: normal;
    font-weight: 700;
    font-size: 20px;
    line-height: 23px;
    letter-spacing: -0.02em;
    font-family: 'GB';
  }
  .body1 {
    font-style: normal;
    font-weight: 500;
    font-size: 20px;
    line-height: 23px;
    letter-spacing: -0.02em;
    font-family: 'GM';
  }
  .body2 {
    font-style: normal;
    font-weight: 300;
    font-size: 18px;
    line-height: 21px;
    letter-spacing: -0.02em;
    font-family: 'GL';
  }
  .body3 {
    font-style: normal;
    font-weight: 300;
    font-size: 16px;
    line-height: 18px;
    letter-spacing: -0.02em;
    color: rgba(0, 0, 0, 0.6);
    font-family: 'GL';
  }
  .linktext {
    font-style: normal;
    font-weight: 300;
    font-size: 16px;
    line-height: 16px;
    display: flex;
    align-items: center;
    letter-spacing: -0.02em;
    font-family: 'GL';
  }
`;

export default commonStyles;