/**
 * @file Effect.java
 * @brief エフェクトクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.effectmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.primitive.CustomRectangle;

/**
 * @class Effect
 * @brief エフェクトクラス
 */
public class Effect {

    //! アニメーション
    private AnimeMotion anime;

    //! テクスチャ
    private Texture texture;

    //! 矩形
    private CustomRectangle rect;

    //! 座標
    private Vector2 pos;

    //! サイズ
    private Vector2 size;

    //! 表示フラグ
    private boolean bShow;

    /**
     * @brief コンストラクタ
     * @details 生成時に呼び出し、メンバの初期化を行う
     */
    public Effect(){

        //矩形の初期化
        this.rect = new CustomRectangle();

        //座標の初期化
        this.pos = new Vector2();

        //表示フラグの初期化
        this.bShow = false;

    }

    /**
     * @brief エフェクトの初期化を行う
     * @param spriteBatch バッチャ
     * @param path エフェクト画像のパス
     */
    public void initialize(CustomSpriteBatch spriteBatch, String path){

        texture = new Texture(path);

        ArrayList<AnimeData[]> animeData = new ArrayList();

        //モーションの初期化
        switch (path)
        {

            case EffectInfo.EFFECT_DEFEAT:

                this.size = new Vector2(120f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"ボスの爆発", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0}}, 0,0,5)});

                break;

            case EffectInfo.EFFECT_CALL:

                this.size = new Vector2(192f, 192f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"召喚", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{0,1},{1,1},{2,1},{3,1},{4,1}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_LIFTED:

                this.size = new Vector2(240f, 240f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"憎しみの扉解除", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{0,1},{1,1}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_WARRIORATTACK:

                this.size = new Vector2(64f, 64f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"戦士の攻撃", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_TEMPTATION:

                this.size = new Vector2(192f, 192f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"サキュバスの誘惑", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{0,1},{1,1},{2,1},{3,1},{4,1},{0,2},{1,2},{2,2},{3,2},{4,2},{0,3},{1,3},{2,3},{3,3},{4,3}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_TEMPTATIONSTATE:

                this.size = new Vector2(120f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"誘惑状態", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{0,1},{1,1},{2,1},{3,1},{4,1},{0,2},{1,2},{2,2},{3,2},{4,2},{0,3},{1,3},{2,3},{3,3},{4,3}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_CURSESTATE:

                this.size = new Vector2(120f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"呪い状態", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_MAGECURSE:

                this.size = new Vector2(120f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"魔導士の呪い", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_MAGETEMPTATION:

                this.size = new Vector2(120f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"魔導士の幻覚", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_MAGEICEWALL:

                this.size = new Vector2(320f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"魔導士の氷流壁", size,false, new int[][]{{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_MAGETHUNDERWALL:

                this.size = new Vector2(320f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"魔導士の雷流壁", size,false, new int[][]{{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_ROCKWALL:

                this.size = new Vector2(120f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"ウルフの岩流壁", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_HELLISHESTKING_ROCKWALL:

                this.size = new Vector2(320f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"非道の王の岩流壁", size,false, new int[][]{{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_HELLISHESTKING_EXPLOSIONWALL:

                this.size = new Vector2(320f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"非道の王の爆流壁", size,false, new int[][]{{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_HELLISHESTKING_WINDBEAST:

                this.size = new Vector2(320f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"非道の王のかまいたち", size,false, new int[][]{{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_HELLISHESTKING_EXPLOSION:

                this.size = new Vector2(120f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"非道の王の爆発", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_HELLISHESTKING_WINDMAGIC:

                this.size = new Vector2(120f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"非道の王の風魔法", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_HELLISHESTKING_THUNDERMAGIC:

                this.size = new Vector2(120f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"非道の王の雷魔法", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_HELLISHESTKING_ICEMAGIC:

                this.size = new Vector2(120f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"非道の王の氷魔法", size,false, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,3)});

                break;

            case EffectInfo.EFFECT_HELLISHESTKING_MAGIC:

                this.size = new Vector2(320f, 120f);

                animeData.add(new AnimeData[] {new AnimeData(texture,"非道の王の魔法", size,false, new int[][]{{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7}}, 0,0,3)});

                break;

        }

        this.anime = new AnimeMotion(animeData.remove(0), spriteBatch);

    }

    /**
     * @brief エフェクトの開始を行う
     * @param pos 座標
     */
    public void start(Vector2 pos){

        //表示フラグの設定
        this.bShow = true;

        //座標の設定
        this.pos.set(pos);

    }

    /**
     * @brief エフェクトの開始を行う
     * @param x X座標
     * @param y Y座標
     */
    public void start(float x, float y){

        //表示フラグの設定
        this.bShow = true;

        //座標の設定
        this.pos.set(x, y);

    }

    /**
     * @brief エフェクトの更新を行う
     */
    public void update(){

        if(!this.bShow){

            return;

        }

        float deltaTime = Gdx.graphics.getDeltaTime();

        this.anime.addTime(deltaTime);

        //アニメーションが終了した場合
        if(this.anime.isAnimationEnd()){

            this.bShow = false;

            this.anime.resetStartTime();

        }
    }

    /**
     * @brief エフェクトの更新を行う
     * @param deltaTime 更新時間
     */
    public void update(float deltaTime){

        if(!this.bShow){

            return;

        }

        this.anime.addTime(deltaTime);

        //アニメーションが終了した場合
        if(this.anime.isAnimationEnd()){

            this.bShow = false;

            this.anime.resetStartTime();

        }

    }

    /**
     * @brief エフェクトの描画を行う
     */
    public void render(){

        if(!this.bShow){

            return;

        }

        //エフェクトの描画
        this.anime.render(this.pos.x, this.pos.y);

    }

    /**
     * @brief エフェクトのリソース解放を行う
     */
    public void release(){

        texture.dispose();

    }

    /**
     * @brief 矩形の取得を行う
     * @return CustomRectangle 矩形
     */
    public CustomRectangle getRect(){

        return this.rect.set(this.pos, this.size);

    }

    /**
     * @brief サイズの取得を行う
     * @return Vector2 サイズ
     */
    public Vector2 getSize(){

        return this.size;

    }

    /**
     * @brief 表示フラグの取得を行う
     * @return boolean 表示フラグ
     */
    public boolean isShow(){

        return this.bShow;

    }

}
