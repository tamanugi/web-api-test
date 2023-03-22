# API コーディングテストについて

## はじめに

SpringBoot と Kotlin または Java で REST API の実装をお願いします。  
テスト時間は4時間程度を目安にしています。  
どちらの言語を選んでいただいても大丈夫ですが Kotlin の使用を推奨します。    

制限時間内に全ての API を作り切ることは難しいと思いますので、  
参照系の API と登録系の API を最低1つずつ作成してください。  
また、全てのタスクを行う必要もありません。どのタスクを選んでなぜそうしたのかは後ほどご回答ください。  
実施していただきたいタスクは以下の通りです。

## タスク一覧

1. REST API の作成
2. テストコードの作成
3. openAPI 形式で API 定義の作成
4. ER図の作成
5. クラス図の作成

## インストール済みのライブラリ

- SpringBoot 2.7.6
- SpringBoot starter test
- SpringBoot Exposed
- Spring JDBC
- Spring Web
- Exposed
    - Exposed のテーブル定義クラスを事前に作成済みです（UserTable.kt, PaymentTable.kt）
    - また、Exposed の実装サンプルを UserRepository.kt に記載しておりますので、ご参照ください。
    - Exposed 以外のライブラリを使っていただいても問題ありません。
- Flyway
- 上記以外に必要なライブラリは自由に追加していただいて構いません

## データベースとテーブル

- MySQL
- 接続情報は docker/docker-compose.yml をご参照ください
- `./gradlew flyway` コマンドでデータベースのマイグレションを実施します
- テーブル定義、関連については `database/migration` ディレクトリのマイグレーションファイルをご参照ください

## 作っていただきたい API

どの API を作っていただいても大丈夫です。

1. user_id を指定して users テーブルから取得する API（参照系）
2. user の情報を送信して users テーブルに登録する API（登録系）
3. users テーブルの情報を指定した件数分、かつ、ページングを考慮して取得する API（参照系）
4. payments テーブルを期間指定で取得し、amount の合計を取得する API（参照系）
5. payments テーブルにデータを登録する api を以下の条件を満たした上で作成してください（登録系）
    1. fee_rate は 4%、tax_rate は10% で計算し、billing_amount に計算した金額を設定する
        1. 例) amount が10,000の場合 billing_amount は 10,440 になる
    2. status は 0 で登録する
    3. payments 登録時に payment_bank_accounts も同時にデータを登録する

## その他

- controller, service, repository でパッケージを分けていますが、パッケージの構成は自由ですので、自由に追加、変更して問題ありません
