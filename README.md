﻿#開発環境
Android Studio 2.1<br>
Android v4.3<br>
nexus 7<br>

#状態
鋭意更新中<br>

#遷移
2016/8/11 大まかなプロトタイプ完成<br>
2016/8/15 Optionでの変数値の保持 by Shun<br>
2016/8/15 デバッグ用のbeat円の表示 by Ryo<br>

#予定
画面遷移だけ(完了)<br>
↓<br>
メニュー画面とか書く←いまここ<br>
　　→グラフィックの表示とかも<br>
↓<br>
タッチ位置とか検出するの実装<br>
↓<br>
いろいろ勉強してから仕様の実装
<br>

#やりたいこと
・縦持ち→横持ちの遷移(水の画面を横持ちする場合)<br>
・グラフィック(水面)<br>
・音の処理&加工<br>

#Help!
Canvasで波の表示を行うとボタンが隠れる<br>
 →表裏の画面遷移とOptionの呼び出し不可 要対策<br>
タッチ座標でintent呼んだらいける(?)</br>
 →いけました やったぜ。<br>
 Canvasの波の上に画像表示させるの<br>
  →システム系のレイヤ使えばいいかもしれんけど未確認&反応するか不明<br>
  →GraphicViewの編集で完遂 やったぜ。
