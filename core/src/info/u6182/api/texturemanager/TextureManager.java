/**
 * @file TextureManager.java
 * @brief テクスチャ管理クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.texturemanager;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.u6182.api.AssetInfo;

/**
 * @class TextureManager
 * @brief テクスチャ管理クラス
 */
public class TextureManager {

    //! 自身のインスタンス
    private static final TextureManager textureManager = new TextureManager();

    //! テクスチャリスト
    private Map<String, Texture> textureList = new HashMap<>();

    /**
     * @brief コンストラクタ
     * @details テクスチャマネージャの初期化を行う
     */
    private TextureManager(){
        ;
    }

    /**
     * @brief load
     * @param textureInfo テクスチャ情報
     * @details テクスチャの読み込むを行う
     */
    public void load(AssetInfo textureInfo){

        ArrayList<String> list = textureInfo.getInfo();

        int len = list.size();

        for(int i = 0;i < len;i++){

            this.textureList.put(list.get(i), new Texture(list.get(i)));

        }

    }

    /**
     * @brief release
     * @details テクスチャの解放を行う
     */
    public void release(){

        for(Texture texture : this.textureList.values()){

            texture.dispose();

        }

        this.textureList.clear();

    }

    /**
     * @brief getTextureManager
     * @return TextureManager テクスチャマネージャのインスタンス
     * @details テクスチャマネージャのインスタンスの取得を行う
     */
    public static TextureManager getTextureManager(){

        return textureManager;

    }

    /**
     * @brief getTexture
     * @param path テクスチャのパス
     * @return Texture テクスチャ
     * @details テクスチャの取得を行う
     */
    public Texture getTexture(String path){

        return this.textureList.get(path);

    }

}
