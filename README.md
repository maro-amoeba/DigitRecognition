# DigitRecognition  
Writed by kotlin.
手書き文字認識アプリ 

## Overview
Androidアプリとして、kotlinで制作。  
認識機能は、TensorFlowLiteを利用。  
https://codelabs.developers.google.com/codelabs/tensorflow-for-poets-2-tflite/#0  

学習データはMnist-Digitからで、  
データの作成はpythonで行いました。  
kerasでデータのダウンロード、ネットワークの構築、学習、保存を行い、  
tfliteファイルへの変換を行った後、アプリへ組み込みます。  

コードの構成は  
-メインアクティビティクラス  
-お絵描き用のカスタムビュークラス  
-識別用のクラス  
の三つのクラスから成ります。  

## ザックリ文字識別までの解説  
お絵描き用のカスタムビュークラスから描き終わったBitmap形式の画像を取得し、  
それをTensorflowLiteが認識できる形式であるByteBuffer形式に変換します。  
クラスラベル(0~9)と一緒に、変換された画像を識別器に与えると、結果の配列が出力されます。  
その中で最も可能性の高いとされた値を取り出し、Android画面に表示しています。

## サンプルイメージ
<img src="https://user-images.githubusercontent.com/37995730/50738114-eb210880-1213-11e9-8d9a-e8c49da77101.png" width="320px"> 
