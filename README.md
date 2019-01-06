# DigitRecognition  
Writed by kotlin.
手書き文字認識アプリ 

## Overview
Androidアプリとして、kotlinで制作。  
認識機能は、TensorFlowLiteを利用。  
https://codelabs.developers.google.com/codelabs/tensorflow-for-poets-2-tflite/#0  

学習データはMnist-Digitからで、  
データの作成にはpythonで行いました。  
kerasを用いてデータのダウンロード、ネットワークの構築、学習、保存を行い、  
tfliteファイルへの変換を行った後、アプリへ組み込みます。  

コードの構成は  
-メインアクティビティクラス  
-お絵描き用のカスタムビュークラス  
-識別用のクラス  
の三つのクラスから成ります。  

## サンプルイメージ
<img src="https://user-images.githubusercontent.com/37995730/50738114-eb210880-1213-11e9-8d9a-e8c49da77101.png" width="320px">
