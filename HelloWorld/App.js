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
  UIManager,
  findNodeHandle,
  NativeModules,
  DeviceEventEmitter
} from 'react-native';

import CustomViewModule from './CustomViewModule';
import CustomPlayerModule from './CustomPlayerModule';


const { CustomModule } = NativeModules;



export default class App extends React.Component {

  viewRef = React.createRef();

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

  _start() {

  }

  _stop() {
    console.log('Stop....', this.viewRef);
    // UIManager.dispatchViewManagerCommand(
    //   findNodeHandle(this.viewRef.current),
    //   "0", []
    // );
  }


  render() {

    return (


      <View style={{ flex: 1, justifyContent: 'center', alignContent: 'center' }}>


        <CustomPlayerModule
          style={{ width: '100%', height: '30%' }}
          uid={{ "uid": "AAZ9S6VZTBBA12RG111A", "pw": "j9ofis" }}
        />

        <View style={{ justifyContent: 'center', alignContent: 'center', flexDirection: 'row' }}>
          <Button
            style={{ flex: 1 }}
            onPress={this._start}
            title="Start" />

          <Button
            style={{ flex: 1 }}
            onPress={this._stop}
            title="Stop" />
        </View>

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
