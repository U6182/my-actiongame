/**
 * @file AssetInfo.java
 * @brief アセット情報を管理する抽象クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api;

import java.util.ArrayList;

/**
 * @class AssetInfo
 * @brief アセット情報を管理する抽象クラス
 */
public abstract class AssetInfo {

    //! パスリスト
    protected ArrayList<String> pathList;

    /**
     * @brief release
     * @details パスリストのクリアを行う
     */
    public void release(){

        this.pathList.clear();

    }

    /**
     * @brief getInfo
     * @return ArrayList<String> パスリスト
     * @details パスリストの取得を行う
     */
    public ArrayList<String> getInfo(){

        return this.pathList;

    }

}
