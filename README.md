# ReinforcementLearningInAutomatonGame
オートマトンを用いたシューティングゲーム強化学習を作りました。
ダメージを受けると報酬-1。三回ダメージを受けると死にますが、その時は報酬-5。
学習にはQ-Learningを用いています。学習率は0.5、割引率は0.5だったはず。

## つかいかた
Java AppletViewerを用意してください。
$ appletviewer mainApplet.java
で起動します。agent君(青いやつ)が頑張る様子を見ましょう。
