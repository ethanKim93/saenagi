import { api, fileApi } from './index';

class PaymentAPI {
  // OCR API 이용한 출력 요청
  // withdraw(receipt) {
  //   return ocrApi.post(`/document/receipt`, JSON.stringify(receipt));
  // }
  // 영수증 첨부
  attachReceipt(campaignId, receipt) {
    return fileApi.post(`/payment/receipt/${campaignId}`, receipt);
  }
}

export default new PaymentAPI();
