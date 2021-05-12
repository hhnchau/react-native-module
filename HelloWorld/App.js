/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  Button,
  View,
  NativeModules,
  DeviceEventEmitter
} from 'react-native';

import CustomViewModule from './CustomViewModule'



const { CustomModule } = NativeModules;



export default class App extends React.Component {
  _onPressButton() {
    CustomModule.show();
  }

  _onToast(event) {
    console.log("AAAA", event.nativeEvent.nativeSendEvent)
  }

  _sendEventToNative() {
    CustomModule.doCallbackTask(11, (name, email, age) => {
      console.log("AAAA", name + "~" + email + "~" + age)
    },
      (err) => {
        console.log("AAAA", err)
      }
    )
  }

  async _sendEventToNativePromise() {
    try {
      let result = await CustomModule.doPromiseTask(11);
      console.log("AAAA", result);
    } catch (error) {
      console.log("AAAA", error);
    }
  }

  async componentDidMount() {
    DeviceEventEmitter.addListener("eventA", event => {
      console.log("AAAA", JSON.stringify(event));
    })
  }


  render() {

    return (

      <View style={{ flex: 1, justifyContent: 'center', alignContent: 'center' }}>

        <Button
          onPress={this._sendEventToNativePromise}
          title="Send Event To Native Promise" />

        <Button
          onPress={this._sendEventToNative}
          title="Send Event To Native" />


        <Button
          onPress={this._onPressButton}
          title="Show Toast From Native" />

        <CustomViewModule
          style={{ flex: 1, justifyContent: 'center', alignContent: 'center' }}
          message={"React send Nessage"}
          onClick={this._onToast}
        />

      </View>

    );
  }
}
