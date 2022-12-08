# API コーディングテストについて

## はじめに
SpringBoot と Java または Kotlin で REST API の実装をお願いします。  
どちらの言語を選んでいただいても大丈夫です。  
制限時間内に全ての API を作り切ることは不可能なので、以下のタスクのうち何をやっていただいても問題ありません。  
また、全ての作業を行う必要もありません。どのタスクを選んでなぜそうしたのかは後ほどご回答ください。

1. openAPI 形式で API 定義の作成
2. ER図の作成
3. クラス図の作成
4. REST API の作成
5. テストコードの作成

## インストール済みのライブラリ
- SpringBoot 2.7.6
- SpringBoot starter test
- Spring Data JPA
- Spring JDBC
- Spring Web
- Flyway

## データベースとテーブル
- MySQL
- 接続情報は docker/docker-compose.yml をご参照ください
- `./gradlew flyway` コマンドでデータベースのマイグレションを実施します
- テーブル定義、関連については `database/src/migration` ディレクトリのマイグレーションファイルをご参照ください

## 作っていただきたい API

どの API を作っていただいても大丈夫です。  
1. user_id を指定して users テーブルから取得する API
2. user の情報を送信して users テーブルに登録する API
3. users テーブルの情報を指定した件数分、かつ、ページングを考慮して取得する API
4. payments テーブルを期間指定で取得し、amount の合計を取得する API