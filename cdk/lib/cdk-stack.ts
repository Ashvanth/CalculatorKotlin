import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { Code, Function, Runtime } from 'aws-cdk-lib/aws-lambda';
import * as apigateway from 'aws-cdk-lib/aws-apigateway';
import { Duration } from 'aws-cdk-lib';

export class CalculatorKotlinAPI extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const calculatorFunction = new Function(this, 'CalculatorKotlinFunction', {
      runtime: Runtime.JAVA_17,
      handler: 'com.demo.dnb.calculatorapi.controller.LambdaHandler::handleRequest',
      code: Code.fromAsset('C:\\Users\\AB73660\\Downloads\\kotlinDemo\\CalculatorAPIkotlin\\target\\CalculatorAPI-0.0.1-SNAPSHOT-aws.jar'),
      timeout: Duration.minutes(10),
    });

    const api = new apigateway.RestApi(this, 'myCalculatorKotlinAPI', {
      deployOptions: {
        stageName: 'v3',
      },
    });

    const calculatorResource = api.root;

    const postMethod = calculatorResource.addMethod('POST', new apigateway.LambdaIntegration(calculatorFunction), {
      requestParameters: {
        'method.request.path.value1': true,
        'method.request.path.value2': true,
        'method.request.path.operator': true,
      },
    });

    const requestBodyModel = api.addModel('RequestBodyModel', {
      contentType: 'application/json',
      schema: {
        schema: apigateway.JsonSchemaVersion.DRAFT4,
        type: apigateway.JsonSchemaType.OBJECT,
        properties: {
          value1: { type: apigateway.JsonSchemaType.INTEGER },
          value2: { type: apigateway.JsonSchemaType.INTEGER },
          operator: { type: apigateway.JsonSchemaType.STRING },
        },
        required: ['value1', 'value2', 'operator'],
      },
    });

   // postMethod.addRequestModel('application/json', requestBodyModel);
  }
}
