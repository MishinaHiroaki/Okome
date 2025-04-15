2025/01/31
・ブラウザからのリクエストを処理し、レスポンスを返す機能を実装
下記のER図を基にデータベースを作成し、スプレットシート(250131)に今回行った4つの実装を説明しています。

ER図
https://drive.google.com/file/d/1y-LIiDvhRkxeawQA8vyzdG6vfiJgN9FN/view?usp=sharing
スプレットシート
https://docs.google.com/spreadsheets/d/1ZHdBCyCjK46PVn01pzcgYpZs52GLShjER9NHyddiEWA/edit?usp=sharing

------
2025/02/12
・Entityのクラス名、単数形に修正
・data.sqlを使ってアプリ起動時にお米の情報とかの初期データを挿入
　->Okome データベースを作成してもらえれば初期データを挿入できるようにファイルを書きました！

2025/02/13
gitからダウンロードすると
Caused by: java.sql.SQLSyntaxErrorException: Unknown column 'created_at' in 'field list'
Caused by: java.sql.SQLSyntaxErrorException: Unknown column 'updated_at' in 'field list'
というエラーが発生した。

以下の原因が考えられる
spring.jpa.hibernate.ddl-auto=update が動いていたため、三品の環境では Hibernate により created_at、updated_at カラムが自動追加された可能性が高い。
解決策として schema.sql を追加し、spring.jpa.hibernate.ddl-auto=none に変更 することで、環境依存をなくした。

2025/02/20 
top.html の検索結果表示を、テーブル構造から整ったレイアウトに変更した。




メモ　cssを更新するコマンド 
 sass --watch src/main/resources/static/sass/style.scss:src/main/resources/static/css/style.css
 sass --watch src/main/resources/static/sass/style_orderx.scss:src/main/resources/static/css/style_orderx.css


 gitにupする時のコマンド
 cd /Applications/Eclipse_2024-09.app/Contents/workspace/Okome_EC_Wiz/
 git pull
 git add .
 git commit -m "コメント"
 git push

2025/02/25
画面遷移図
https://drive.google.com/file/d/1Qp4w-oCLjP1Oa5kP1nTQwPhs-0wDGLSH/view?usp=sharing


2025/02/28 (03/03更新)
商品詳細ボタン押下で商品詳細ベージに遷移し、カートに入れるボタンを押下で、カートに入れた商品の閲覧及び購入検討の商品削除をする機能を実装した。カートに入れた商品はcartsテーブルに挿入され削除もされる。

2025/03/05
【現在実装できている機能】
・「トップページ」から商品を検索できる。
・各商品の(商品詳細ページ)ボタン押下すると、「商品詳細ページ」へ遷移できる。
・「商品詳細ページ」で購入数を選択して、(カートに入れる)ボタン押下で、「カートの中身」へ遷移できる。
　->この遷移のタイミングで、cartsテーブルにデータを挿入
・「カートの中身」のページで(注文手続き)ボタン押下で、「注文画面1」へ遷移できる。
【まだ未実装】
・「注文画面1」ページでご注文者の情報を入力後に(ご注文内容の確認へ)ボタン押下で「注文画面2」へ遷移する。
 ▼補足
「注文画面2」では、購入希望の商品とご注文者の情報の最終確認をするための画面です。
「注文画面2」の(注文確定)ボタン押下すると、order_itemsテーブルとordersテーブルにデータを挿入して,
 cartsテーブル内のデータを全て削除する予定です。
・ログイン機能はまだないです

2025/03/10
以下の機能を実装した
・「注文画面1」ページでご注文者の情報を入力後に(ご注文内容の確認へ)ボタン押下で「注文画面2」へ遷移する
　*「注文画面2」では、購入希望の商品とご注文者の情報の最終確認をするための画面です。

2025/03/12
【gitに上げたファイルをダウンロードして、eclipseで開く方法】
1.プロジェクトを格納するファイルを作成
/Applications/Eclipse_2024-09.app/Contents/新しいファイル
2.eclipseにファイルインポート準備
①ファイル>インポート>Gitからプロジェクト(スマート・インポート)
②クローンURI>ロケーションURIにCloneのHTTPS(https://github.com/MishinaHiroaki/Okome.git)
③ローカル宛先のディレクトリーに/Applications/Eclipse_2024-09.app/Contents/新しいファイルを選択

3.プロジェクトのビルド
ターミナルでプロジェクトのルートディレクトリへ移動します。
cd /Applications/Eclipse_2024-09.app/Contents/workspace/0310update_clone

Gradleがインストールされたら、以下のコマンドで gradle-wrapper.jar を再生成します。
gradle wrapper
これで、gradle-wrapper.jar や必要なWrapper設定ファイルが自動で生成されます。

ビルド実行！
./gradlew build

4.アプリケーションの起動
java -jar build/libs/0310update_clone-0.0.1-SNAPSHOT.jar

5.動作確認
http://localhost:8080

【ターミナルでJava 21（openjdk@21）を使用するために必要な環境変数を設定】
1. export JAVA_HOME=/opt/homebrew/opt/openjdk@21
•	JAVA_HOME は、システムが Java 開発キット（JDK）のインストール場所を見つけるための環境変数です。

2. export PATH=$JAVA_HOME/bin:$PATH
•	PATH は、システムが実行可能ファイルを検索するディレクトリのリストです。
•	このコマンドは、Java 21 のバイナリディレクトリ ($JAVA_HOME/bin) を PATH に追加します。これにより、java や javac などのコマンドをターミナルで直接実行できるようになります。
•	$PATH の前に :$JAVA_HOME/bin を追加することで、Java 21 のコマンドが優先的に使用されるように設定されます。
•: $PATH の部分は、現在の PATH 環境変数に既存のディレクトリパスを保持したまま、新しいディレクトリパスを追加するために使われます。

.zshrc ファイルはZshシェルが起動する際に自動的に読み込まれます。
Zshシェルはこのファイルを探して実行する設定を適用します。
source ~/.zshrc //新しい設定が即座にシェルに反映されます。


【文字列検索】protobufをどのファイルに書かれているか検索したい時
grep -r "protobuf" /Applications/Eclipse_2024-09.app/Contents/workspace/Okome_EC_Wiz


2025/03/13
・Order2.htmlから注文者情報を送信し、コントローラで受け取り、passwordをハッシュ化して、usersテーブルに保存できた。
//0313_update1としてgitに上げた。

・「注文画面2」の(注文確定)ボタン押下すると、order_itemsテーブルとordersテーブルにデータを挿入して,
 cartsテーブル内のデータを全て削除できた。
//0313_update2としてgitに上げた。

2025/03/14
・「注文画面3」のレイアウトを整えた
//0314_update1

2025/03/20
・ページネーション機能を追加した
//0320_update1

2025/03/24
・ページネーションのレイアウトを修正した
//0324_update1

2025/03/25
・郵便番号api機能を実装
//0325_update1

【Thymeleafで値を受け取る場合のメモ】
◉Thymeleafで${...}の式をHTMLテキストに埋め込む場合、[[${...}]]とする
例：<p>ログイン状態: [[${session.userName}]]</p>

◉属性値に使う場合はth:text(表示以外の用途には th: 系が適しています。)
例：<p th:text="${session.userName}"></p>

【data.sqlのinsert文について】
select * from products;を実行して、insert文として、exportすれば、その出力結果をdata.sqlにコピペすればいい

2025/03/25
・ログイン機能,実装途中
//0325_update2

2025/03/31
・ログイン機能を実装作成
//0331_update1

2025/04/04

【ログイン機能】
●ゲストユーザー(初回)
-> userIdがないため、sessionIdでカートを管理する

●ログインユーザー(2パターン)
1.ログイン前にカートに追加 (ゲストユーザーの状態でカートに商品を入れる、その後ログインする)
-> sessionIdでカートを管理する、ログイン後にuserIdに紐付けて管理する

2.ログイン後にカートに追加 (ログインユーザーの状態でカートに商品を入れる)
-> userIdでカートを管理する

*ログインユーザのユーザ情報は自動入力され、（名前・メアド）は編入不可で（郵便番号・住所・電話番号）は編集可能


2025/04/08
決済システムと統合した　//0408_update1

stripe_session_idをDBに保存した //0408_update2

【linuxサーバーにデプロイする手順】
1.Desktopへ移動し以下を実行すると、Okomeフォルダが作成される
git clone https://github.com/MishinaHiroaki/Okome.git

2.ビルドを実行(build/libsにjarファイルが作成される、ソースコード(.javaファイル)を実行できる形(jarファイル)に変更する作業をビルドという
gradle build

3.ビルドが成功すると、bulid/libs/にjarファイルが作成される

4.scpコマンドでjarファイルをサーバにアップロードする (secure copyのこと、自分のPCとサーバーの間でファイルコピー)
scp bulid/libs/xxxx-SNAPSHOT.jar root@サーバのIPアドレス:/root
例：scp build/libs/Okome-0.0.1-SNAPSHOT.jar root@160.251.206.176:/root/
scp [送りたいファイルの場所] [送り先サーバー]:[送り先のパス]

[送り先サーバー](ユーザ名とIP)->root@160.251.206.176

5.ssh root@サーバのIPアドレス　でサーバにログインする　　　サーバのIPアドレスのrootにsshアクセスするってこと

6.パッケージを最新に更新する
yum update -y

7.mysqlをインストール
yum install -y mysql-server

8.mysqlが自動で立ち上がるように設定する
systemctl start mysqld //今すぐMySQLを起動する
systemctl enable mysqld //サーバ起動時にMySQLを自動起動する

9.mysqlにログインする
mysql -u root

10.ログイン後、パスワードを設定する
ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';　
//ALTER USERはユーザ情報を変更する　//'root'@'localhost'　mysqlの中のrootユーザにlocalhost（127.0.0.1）からアクセスするって意味
//IDENTIFIED BY 'password' パスワードをpasswordに設定する

FLUSH PRIVILEGES;　//新しいパスワード情報を即座に反映
EXIT;

11.mysql -u root -p  でパスワードを打ってログインできる

12.firewallでポートの開閉ステータスを確認する
firewall-cmd --list-ports
8080/tcpと表示されなければ閉じている

13
firewall-cmd --permanent --add-port=8080/tcp //8080番ポートをずっと開ける設定を追加
firewall-cmd --reload　//ファイアウォール設定を即反映

14
java -jar Okome_EC_Wiz-0.0.1-SNAPSHOT.jar //springboot起動

15 ブラウザで表示
http://160.251.206.176:8080/

＊ps aux　で動いているアプリが表示
ps aux | grep javaで
root 537684 0.9 44.0 2338736 200164 pts/0 Sl+ 14:46 0:21 java -jar Okome_EC_Wiz-0.0.1-SNAPSHOT.jar
が表示されていればspringboot起動してる


2025/04/09
linuxサーバにデプロイしたが、動かなかった。Login.html ->login.html と修正した

2025/04/10
「初めての方へ」「よくある質問」ページを追加した //0410_update1

jarファイルの中身を確認する、loginと名前があるものを検索している
jar tf build/libs/Okome_EC_Wiz-0.0.1-SNAPSHOT.jar | grep -i login


2025/04/15
【Thymeleafでの注意点】
・eachとifが同じタグにある場合、eachが先に実行される
・eachで対象がnullだとエラーになる
th:blockを使ってif文を先に実行させてnullでないことを確認してからeachする！
コントローラ側でデータがnullの場合は、空リストを渡してnullにならないように対策する

【2つのファイルを比較】
VSコードでコマンドパレット(cmd+sft+p)でCompare Active file  with Savedを選択で比較できる

----
購入履歴ページを作成した//0415_update1
【】

【】
