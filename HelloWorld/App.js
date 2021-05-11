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
