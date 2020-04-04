using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Crustal.Toast.Test.RNCrustalToastTest
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNCrustalToastTestModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNCrustalToastTestModule"/>.
        /// </summary>
        internal RNCrustalToastTestModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNCrustalToastTest";
            }
        }
    }
}
