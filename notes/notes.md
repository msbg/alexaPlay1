Command for exporting:

With something like this in ~/.aws/config  -

[edonica]
aws_access_key_id = <ID>
aws_secret_access_key = <SECRET>

aws --profile edonica --region us-east-1 lambda update-function-code \
  --function-name AlexaDecisionTreeSpeechlet \
  --zip-file fileb://../target/lambdaTest1-1.0-SNAPSHOT.jar

aws lambda invoke \
  --profile edonica --region us-east-1 \
  --invocation-type RequestResponse \
  --log-type Tail \
  --function-name AlexaDecisionTreeSpeechlet \
  --payload file://launch.json \
  output.json



