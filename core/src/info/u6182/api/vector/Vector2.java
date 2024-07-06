/**
 * @file Vector2.java
 * @brief 2次元ベクトルを表すクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.vector;

/**
 * @class Vector2
 * @brief 2次元ベクトルを表すクラス
 */
public class Vector2 {

    //! 度数で指定された角度を変換
    public static float TO_RADIANS = (1 / 180.0f) * (float)Math.PI;

    //! ラジアンで指定された角度を変換
    public static float TO_DEGREES = (1 / (float)Math.PI) * 180;

    //! X, Y座標
    public float x, y;

    /**
     * @brief コンストラクタ
     * @details デフォルトのコンストラクタ
     */
    public Vector2(){
        ;
    }

    /**
     * @brief コンストラクタ
     * @param x X座標
     * @param y Y座標
     * @details X座標とY座標を指定してベクトルの初期化を行う
     */
    public Vector2(float x, float y){

        this.x = x;
        this.y = y;

    }

    /**
     * @brief コンストラクタ
     * @param other 他のベクトル
     * @details 他のベクトルをコピーしてベクトルの初期化を行う
     */
    public Vector2(Vector2 other){

        this.x = other.x;
        this.y = other.y;

    }

    /**
     * @brief cpy
     * @return Vector2 コピーされたベクトル
     * @details 元のベクトルを変更せずに新しいベクトルのコピーを行う
     */
    public Vector2 cpy(){

        //元のVector2の値を変更せずに操作可能
        return new Vector2(x, y);

    }

    /**
     * @brief set
     * @param x X座標
     * @param y Y座標
     * @return Vector2 自身のインスタンス
     * @details ベクトルのX座標とY座標の設定を行う
     */
    public Vector2 set(float x, float y){

        this.x = x;
        this.y = y;

        //自身を返すので演算を連結することが可能
        return this;

    }

    /**
     * @brief set
     * @param other 他のベクトル
     * @return Vector2 自身のインスタンス
     * @details 他のベクトルの設定を行う
     */
    public Vector2 set(Vector2 other){

        this.x = other.x;
        this.y = other.y;

        //自身を返すので演算を連結することが可能
        return this;

    }

    /**
     * @brief add
     * @param x X座標
     * @param y Y座標
     * @return Vector2 自身のインスタンス
     * @details ベクトルにX座標とY座標の加算を行う
     */
    public Vector2 add(float x, float y){

        this.x += x;
        this.y += y;

        //自身を返すので演算を連結することが可能
        return this;

    }

    /**
     * @brief add
     * @param other 他のベクトル
     * @return Vector2 自身のインスタンス
     * @details 他のベクトルの加算を行う
     */
    public Vector2 add(Vector2 other){

        this.x += other.x;
        this.y += other.y;

        //自身を返すので演算を連結することが可能
        return this;

    }

    /**
     * @brief sub
     * @param x X座標
     * @param y Y座標
     * @return Vector2 自身のインスタンス
     * @details ベクトルからX座標とY座標の減算を行う
     */
    public Vector2 sub(float x, float y){

        this.x -= x;
        this.y -= y;

        //自身を返すので演算を連結することが可能
        return this;

    }

    /**
     * @brief sub
     * @param other 他のベクトル
     * @return Vector2 自身のインスタンス
     * @details 他のベクトルの減算を行う
     */
    public Vector2 sub(Vector2 other){

        this.x -= other.x;
        this.y -= other.y;

        //自身を返すので演算を連結することが可能
        return this;

    }

    /**
     * @brief mul
     * @param scalar スカラー値
     * @return Vector2 自身のインスタンス
     * @details ベクトルにスカラー値の乗算を行う
     */
    public Vector2 mul(float scalar){

        this.x *= scalar;
        this.y *= scalar;

        //自身を返すので演算を連結することが可能
        return this;

    }

    /**
     * @brief len
     * @return float ベクトルの長さ
     * @details ベクトルの長さの取得を行う
     */
    public float len(){

        //ベクトルの長さを返す
        return (float) Math.sqrt(x * x + y * y);

    }

    /**
     * @brief nor
     * @return Vector2 自身のインスタンス
     * @details ベクトルの正規化を行う
     */
    public Vector2 nor(){

        //ベクトルの長さを取得
        float len = len();

        //0の徐算を避ける
        if(len != 0){

            //ベクトルを単位長に正規化する
            this.x /= len;
            this.y /= len;

        }

        //自身を返すので演算を連結することが可能
        return this;

    }

    /**
     * @brief angle
     * @return float 角度
     * @details ベクトルの角度の取得を行う
     */
    public float angle(){

        //ラジアンを度数に変換
        float angle = (float)Math.atan2(y, y) * TO_DEGREES;

        //負数の場合pai * 2(360)を足して補正する(0～360)の範囲に収める
        if(angle < 0){

            angle += 360;

        }

        return angle;

    }

    /**
     * @brief rotate
     * @param angle 角度
     * @return Vector2 自身のインスタンス
     * @details ベクトルを指定された角度だけ回転を行う
     */
    public Vector2 rotate(float angle){

        //度数をラジアンに変換
        float rad = angle * TO_RADIANS;

        float cos = (float)Math.cos(rad);

        float sin = (float)Math.sin(rad);

        float newX = this.x * cos - this.y * sin;
        float newY = this.x * sin + this.y * cos;

        //原点を中心に指定された角度だけベクトルを回転
        this.x = newX;
        this.y = newY;

        //自身を返すので演算を連結することが可能
        return this;

    }

    /**
     * @brief dist
     * @param x X座標
     * @param y Y座標
     * @return float 距離
     * @details 指定された座標との距離の取得を行う
     */
    public float dist(float x, float y){

        float distX = this.x - x;
        float distY = this.y - y;

        //指定されたベクトルとの距離を返す
        return (float)Math.sqrt(distX * distX + distY * distY);

    }

    /**
     * @brief dist
     * @param other 他のベクトル
     * @return float 距離
     * @details 他のベクトルとの距離の取得を行う
     */
    public float dist(Vector2 other){

        float distX = this.x - other.x;
        float distY = this.y - other.y;

        //指定されたベクトルとの距離を返す
        return (float)Math.sqrt(distX * distX + distY * distY);

    }

    /**
     * @brief distSquared
     * @param x X座標
     * @param y Y座標
     * @return float 距離の2乗
     * @details 指定された座標との距離の2乗の取得を行う
     */
    public float distSquared(float x, float y){

        float distX = this.x - x;

        float distY = this.y - y;

        //二つのベクトル間の距離の2乗を返す
        return distX * distX + distY * distY;

    }

    /**
     * @brief distSquared
     * @param other 他のベクトル
     * @return float 距離の2乗
     * @details 他のベクトルとの距離の2乗の取得を行う
     */
    public float distSquared(Vector2 other){

        float distX = this.x - other.x;

        float distY = this.y - other.y;

        //二つのベクトル間の距離の2乗を返す
        return distX * distX + distY * distY;

    }







}
