Command for exporting:

Contents of ~/.aws/config:

```
[edonica]
aws_access_key_id = <ID>
aws_secret_access_key = <SECRET>
```

Deploy our JAR file:
```
aws --profile edonica --region us-east-1 lambda update-function-code \
  --function-name AlexaDecisionTreeSpeechlet \
  --zip-file fileb://../target/DecisionTreeCore-1.0-SNAPSHOT.jar
```

Invoke JAR given input JSON:
```
aws lambda invoke \
  --profile edonica --region us-east-1 \
  --invocation-type RequestResponse \
  --log-type Tail \
  --function-name AlexaDecisionTreeSpeechlet \
  --payload file://launch.json \
  output.json
```

Pretty print output:
`jq . output.json`


Generate graph from .dot file:
`dot DecisionTree.dot -Tpng  -ofile -O`

