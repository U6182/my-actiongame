/**
 * @file StageInfo.java
 * @brief ステージ情報クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.stage;

import info.u6182.api.primitive.CustomRectangle;

/**
 * @class StageInfo
 * @brief ステージ情報クラス
 */
public class StageInfo {

    //! ステージの矩形
    public CustomRectangle chipRect;

    //! ステージ情報
    public final byte[] chipData;

    //! ステージチップのサイズ
    public int chipSize;

    //! ステージの列の数
    public int xCount;

    //! ステージの行の数
    public int yCount;

    /**
     * @brief コンストラクタ
     * @param chipData ステージ情報
     * @param chipSize ステージチップのサイズ
     * @param xCount ステージの列の数
     * @param yCount ステージの行の数
     */
    public StageInfo(final byte[] chipData, int chipSize, int xCount, int yCount){

        //ステージの矩形の初期化
        this.chipRect = new CustomRectangle();

        //ステージ情報の設定
        this.chipData = chipData;

        //ステージチップのサイズの設定
        this.chipSize = chipSize;

        //ステージの列の数の設定
        this.xCount = xCount;

        //ステージの行の数の設定
        this.yCount = yCount;

    }

}
