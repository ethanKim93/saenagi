import { api, tokenApi } from './index';
class CampaignAPI {
  //캠페인 리스트 전체 조회
  getCampaignAll() {
    return api.get(`campaigns`);
  }

  // 캠페인 타입별 리스트 조회
  getCampaignByType(type) {
    return api.get(`campaigns?isEnd=false&type=${type}`);
  }

  //캠페인 리스트 전체 상태별 조회(정렬 포함)
  getCampaignIsend(isEnd, sortType, desc) {
    return api.get(`campaigns?isEnd=${isEnd}&sortType=${sortType}&desc=${desc}`);
  }

  // 캠페인 상세조회
  getCampaignById(campaignId) {
    return api.get(`campaign/detail/${campaignId}`);
  }

  // 캠페인 검색(정렬 포함)
  searchByKeyword(searchWord, sortType, desc) {
    return api.get(`campaigns?searchWord=${searchWord}&sortType=${sortType}&desc=${desc}`);
  }
  // 생성한 캠페인리스트
  getOwnedCampaign() {
    return tokenApi.get(`campaigns/owned/`);
  }

  // 캠페인 태그 조회
  getCampaignTag(campaignId) {
    return api.get(`campaigns/tags/${campaignId}`);
  }
}

export default new CampaignAPI();
