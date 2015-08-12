package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import android.database.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.maepaysoh.maepaysohsdk.api.CandidateService;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.db.CandidateDao;
import org.maepaysoh.maepaysohsdk.models.Candidate;
import org.maepaysoh.maepaysohsdk.models.CandidateReturnObject;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by yemyatthu on 8/11/15.
 */
public class CandidateAPIHelper{
  private RestAdapter mCandidateRestAdapter;
  private CandidateService mCandidateService;
  private CandidateDao mCandidateDao;
  private Context mContext;
  public CandidateAPIHelper(String token,Context context){
    mCandidateRestAdapter = RetrofitHelper.getResAdapter(token);
    mCandidateService = mCandidateRestAdapter.create(CandidateService.class);
    mContext = context;
  }

  /**
   *
   * @param callback
   */
  public void getCandidatesAsync(Callback<CandidateReturnObject> callback){
    getCandidatesAsync(false, true, 1, 15, callback);
  }

  /**
   *
   * @param withParty
   * @param callback
   */
  public void getCandidatesAsync(boolean withParty, Callback<CandidateReturnObject> callback){
    getCandidatesAsync(withParty, true, 1, 15, callback);
  }


  /**
   *
   * @param withParty
   * @param unicode
   * @param callback
   */
  public void getCandidatesAsync(Boolean withParty, boolean unicode,
      Callback<CandidateReturnObject> callback){
    getCandidatesAsync(withParty, unicode, 1, 15, callback);
  }

  /**
   *
   * @param withParty
   * @param unicode
   * @param firstPage
   * @param callback
   */
  public void getCandidatesAsync(Boolean withParty, boolean unicode, int firstPage,
      Callback<CandidateReturnObject> callback){
    getCandidatesAsync(withParty, unicode, firstPage, 15, callback);
  }

  /**
   *
   * @param withParty
   * @param unicode
   * @param firstPage
   * @param perPage
   * @param callback
   */
  public void getCandidatesAsync(boolean withParty, boolean unicode, int firstPage, int perPage,
      Callback<CandidateReturnObject> callback){
    Map<CandidateService.PARAM_FIELD,String> optionParams = new HashMap<>();
    if(withParty) {
      optionParams.put(CandidateService.PARAM_FIELD._with, Constants.WITH_PARTY);
    }
    if(unicode) {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.UNICODE);
    }else{
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(CandidateService.PARAM_FIELD.page,String.valueOf(firstPage));
    optionParams.put(CandidateService.PARAM_FIELD.per_page,String.valueOf(perPage));
    mCandidateService.listCandidatesAsync(optionParams, callback);
  }

  /**
   *
   */
  public List<Candidate> getCandidates(boolean cache){
    return getCandidates(false, true, 1, 15, cache);
  }

  /**
   *
   * @param withParty
   */
  public List<Candidate> getCandidates(boolean withParty,boolean cache){
    return getCandidates(withParty, true, 1, 15, cache);
  }

  /**
   *
   * @param firstPage
   */
  public List<Candidate> getCandidates(int firstPage,boolean cache){
    return getCandidates(true, true, firstPage, 15, cache);
  }

  /**
   *
   * @param withParty
   * @param unicode
   */
  public List<Candidate> getCandidates(Boolean withParty, boolean unicode,boolean cache){
    return getCandidates(withParty, unicode, 1, 15, cache);
  }

  /**
   *
   * @param withParty
   * @param unicode
   * @param firstPage
   */
  public List<Candidate> getCandidates(Boolean withParty, boolean unicode, int firstPage,boolean cache){
    return getCandidates(withParty, unicode, firstPage, 15,cache);
  }

  /**
   *
   * @param withParty
   * @param unicode
   * @param firstPage
   * @param perPage
   * @return
   */
  public List<Candidate> getCandidates(boolean withParty, boolean unicode, int firstPage, int perPage,
      boolean cache){
    mCandidateDao = new CandidateDao(mContext);
    Map<CandidateService.PARAM_FIELD,String> optionParams = new HashMap<>();
    if(withParty) {
      optionParams.put(CandidateService.PARAM_FIELD._with, Constants.WITH_PARTY);
    }
    if(unicode) {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.UNICODE);
    }else{
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(CandidateService.PARAM_FIELD.page,String.valueOf(firstPage));
    optionParams.put(CandidateService.PARAM_FIELD.per_page,String.valueOf(perPage));
    CandidateReturnObject returnObject = mCandidateService.listCandidates(optionParams);
    if(cache){
      for (Candidate data : returnObject.getData()) {
        try {
          mCandidateDao.createCandidate(data);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return returnObject.getData();
  }

  /**
   *
   * @param candidateId
   * @param callback
   */
  public void getCandidateByIdAsync(String candidateId, Callback<CandidateReturnObject> callback){
    getCandidateByIdAsync(candidateId, true, true, callback);
  }

  /**
   *
   * @param candidateId
   * @param withParty
   * @param callback
   */
  public void getCandidateByIdAsync(String candidateId, boolean withParty,
      Callback<CandidateReturnObject> callback){
    getCandidateByIdAsync(candidateId, withParty, true, callback);
  }

  /**
   *
   * @param candidateId
   * @param withParty
   * @param unicode
   * @param callback
   */
  public void getCandidateByIdAsync(String candidateId, Boolean withParty, boolean unicode,
      Callback<CandidateReturnObject> callback){
    Map<CandidateService.PARAM_FIELD,String> optionParams = new HashMap<>();
    if(withParty) {
      optionParams.put(CandidateService.PARAM_FIELD._with, Constants.WITH_PARTY);
    }
    if(unicode) {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.UNICODE);
    }else{
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    mCandidateService.getCandidateByIdAsync(candidateId, optionParams, callback);
  }
  /**
   *
   * @param candidateId
   */
  public List<Candidate> getCandidateById(String candidateId,boolean cache){
    return getCandidateById(candidateId, true, true, cache);
  }

  /**
   *
   * @param candidateId
   * @param withParty
   */
  public List<Candidate> getCandidateById(String candidateId, boolean withParty,boolean cache){
    return getCandidateById(candidateId, withParty, true, cache);
  }

  /**
   *
   * @param candidateId
   * @param withParty
   * @param unicode
   */
  public List<Candidate> getCandidateById(String candidateId, Boolean withParty, boolean unicode,boolean cache){
    Map<CandidateService.PARAM_FIELD,String> optionParams = new HashMap<>();
    if(withParty) {
      optionParams.put(CandidateService.PARAM_FIELD._with, Constants.WITH_PARTY);
    }
    if(unicode) {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.UNICODE);
    }else{
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    CandidateReturnObject returnObject = mCandidateService.getCandidateById(candidateId,optionParams);
    if(cache){
      for (Candidate data : returnObject.getData()) {
        try {
          mCandidateDao.createCandidate(data);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
   return returnObject.getData();
  }

  public List<Candidate> getCandidatesFromCache(){
    mCandidateDao = new CandidateDao(mContext);
    return mCandidateDao.getAllCandidateData();
  }
}
