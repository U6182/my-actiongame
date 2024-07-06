/**
 * @file EffectManager.java
 * @brief エフェクトを管理するクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.effectmanager;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.u6182.api.AssetInfo;
import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.primitive.CustomRectangle;

/**
 * @class EffectManager
 * @brief エフェクトを管理するクラス
 */
public class EffectManager {

    //! 自身のインスタンス
    private static final EffectManager EFFECT_MANAGER = new EffectManager();

    //! エフェクト
    private Map<String, Effect[]> effectList = new HashMap<>();

    //! エフェクト数
    private int effectCount;

    //! エフェクトの種類数
    private int effectTypeCount;

    /**
     * @brief コンストラクタ
     */
    private EffectManager(){
        ;
    }

    /**
     * @brief エフェクトの読み込みを行う
     * @param spriteBatch バッチャ
     * @param EffectInfo エフェクト情報
     * @param effectCount エフェクト数
     */
    public void load(CustomSpriteBatch spriteBatch, AssetInfo EffectInfo, int effectCount){

        ArrayList<String> list = EffectInfo.getInfo();

        this.effectTypeCount = list.size();

        this.effectCount = effectCount;

        for(int j = 0;j < this.effectTypeCount;j++){

            String path = list.get(j);

            this.effectList.put(path, new Effect[this.effectCount]);

            for (int i = 0;i < this.effectCount;i++){

                this.effectList.get(path)[i] = new Effect();

                this.effectList.get(path)[i].initialize(spriteBatch, path);

            }

        }

    }

    /**
     * @brief エフェクトの開始を行う
     * @param path エフェクトのパス
     * @param pos 座標
     */
    public void start(String path, Vector2 pos){

        //エフェクトの表示
        for(int i = 0;i < this.effectCount;i++){

            if(this.effectList.get(path)[i].isShow()){

                continue;

            }

            this.effectList.get(path)[i].start(pos);

            return;

        }
    }

    /**
     * @brief エフェクトの開始を行う
     * @param path エフェクトのパス
     * @param x X座標
     * @param y Y座標
     */
    public void start(String path, float x, float y){

        //エフェクトの表示
        for(int i = 0;i < this.effectCount;i++){

            if(this.effectList.get(path)[i].isShow()){

                continue;

            }

            this.effectList.get(path)[i].start(x, y);

            return;

        }
    }

    /**
     * @brief エフェクトの更新を行う
     */
    public void update(){

        List<Effect[]> list = new ArrayList<>(this.effectList.values());

        //エフェクトの更新
        for(int j = 0;j < this.effectTypeCount;j++){

            for(int i = 0;i < this.effectCount;i++){

                list.get(j)[i].update();

            }

        }

    }

    /**
     * @brief エフェクトの更新を行う
     * @param deltaTime 更新時間
     */
    public void update(float deltaTime){

        List<Effect[]> list = new ArrayList<>(this.effectList.values());

        //エフェクトの更新
        for(int j = 0;j < this.effectTypeCount;j++){

            for(int i = 0;i < this.effectCount;i++){

                list.get(j)[i].update(deltaTime);

            }

        }

    }

    /**
     * @brief エフェクトの描画を行う
     */
    public void render(){

        List<Effect[]> list = new ArrayList<>(this.effectList.values());

        //エフェクトの描画
        for(int j = 0;j < this.effectTypeCount;j++){

            for(int i = 0;i < this.effectCount;i++){

                list.get(j)[i].render();

            }

        }

    }

    /**
     * @brief エフェクトのリソース解放を行う
     */
    public void release(){

        List<Effect[]> list = new ArrayList<>(this.effectList.values());

        //エフェクトの描画
        for(int j = 0;j < this.effectTypeCount;j++){

            for(int i = 0;i < this.effectCount;i++){

                list.get(j)[i].release();

            }

        }

        this.effectList.clear();
    }

    /**
     * @brief EffectManagerインスタンスの取得を行う
     * @return EffectManagerのインスタンス
     */
    public static EffectManager getEffectManager(){

        return EFFECT_MANAGER;

    }

    /**
     * @brief 矩形の取得を行う
     * @param path エフェクトのパス
     * @param i インデックス
     * @return CustomRectangle 矩形
     */
    public CustomRectangle getRect(String path, int i){

        return this.effectList.get(path)[i].getRect();

    }

    /**
     * @brief サイズの取得を行う
     * @param path エフェクトのパス
     * @return Vector2 サイズ
     */
    public Vector2 getSize(String path){

        return this.effectList.get(path)[0].getSize();

    }

    /**
     * @brief エフェクトの表示状態の取得を行う
     * @param path エフェクトのパス
     * @return boolean 表示状態
     */
    public boolean isEffect(String path){

        boolean bShow = false;

        //エフェクトの表示
        for(int i = 0;i < this.effectCount;i++){

            if(this.effectList.get(path)[i].isShow()){

                bShow = true;

            }

        }

        return bShow;

    }

}
