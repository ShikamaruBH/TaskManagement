# TaskManagement
## Features:
- Add project to manage tasks which you will do.
- Add a task with name and mark importance of the task, delete task.
- Archive project or move project to trash bin.
- Sync data from Firebase at first time login.
## Preview :
<table>
    <tr>
        <td><b><p align="center">Login Screen<p></b></td>
        <td><b><p align="center">Register Screen<p></b></td>
        <td><b><p align="center">Main Screen<p></b></td>
        <td><b><p align="center">Add Project Screen<p></b></td>
    </tr>
    <tr>
        <td><img src="https://i.pinimg.com/736x/e2/f2/1e/e2f21e3824e44d57135ad636720bf565.jpg" alt="drawing" width="250"/></td>
        <td><img src="https://i.pinimg.com/564x/ac/9c/77/ac9c77be24dcb3b3e5a3df682ff320ca.jpg" alt="drawing" width="250"/></td>
        <td><img src="https://i.pinimg.com/564x/b0/22/b2/b022b28376cb8210c5a14a6af80875e5.jpg" alt="drawing" width="250"/></td>
        <td><img src="https://i.pinimg.com/564x/02/32/d5/0232d52cea01ca0f2f59f96566244fa1.jpg" alt="drawing" width="250"/></td>
    </tr>
</table>

<table>
    <tr>
        <td><b><p align="center">Task Screen<p></b></td>
        <td><b><p align="center">Add Task Screen</b><p></td>
        <td><b><p align="center">Archive Screen<p></b></td>
        <td><b><p align="center">Trash Screen<p></b></td>
    </tr>
    <tr>
        <td><img src="https://i.pinimg.com/564x/51/07/ef/5107efe35277159309788e1ff596a81c.jpg" alt="drawing" width="250"/></td>
        <td><img src="https://i.pinimg.com/564x/00/06/1c/00061c52aa87577badaf08d7a863d1dd.jpg" alt="drawing" width="250"/></td>
        <td><img src="https://i.pinimg.com/564x/a3/93/86/a393867b87eae3a15c83eff771c40964.jpg" alt="drawing" width="250"/></td>
        <td><img src="https://i.pinimg.com/564x/33/c4/43/33c443e1880f6c78e7ccd98f4b5acb6f.jpg" alt="drawing" width="250"/></td>
    </tr>
</table>

## Android Libraries : 
    def room_version = "2.4.2"
    def lifecycle_version = "2.4.0"

    implementation 'androidx.navigation:navigation-runtime-ktx:2.4.2'
    implementation "androidx.fragment:fragment-ktx:1.2.5"

    implementation "androidx.navigation:navigation-compose:2.4.2"
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    
    // To use constraintlayout in compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version"
    
    // LiveData
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"

    // Icon
    implementation "androidx.compose.material:material-icons-extended:$compose_version"

    // Hilt
    implementation "com.google.dagger:hilt-android:2.38.1"
    kapt "com.google.dagger:hilt-compiler:2.38.1"

    // Room
    kapt "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-ktx:$room_version"
    implementation "androidx.room:room-runtime:$room_version"
    implementation "androidx.room:room-rxjava2:$room_version"
    implementation "androidx.room:room-rxjava3:$room_version"
    implementation "androidx.room:room-guava:$room_version"
    testImplementation "androidx.room:room-testing:$room_version"
    implementation "androidx.room:room-paging:2.5.0-alpha01"

    //Navigation
    implementation "androidx.navigation:navigation-compose:2.4.2"

    implementation 'androidx.core:core-ktx:1.7.0'
    implementation "androidx.compose.ui:ui:$compose_version"
    implementation "androidx.compose.material:material:$compose_version"
    implementation "androidx.compose.ui:ui-tooling-preview:$compose_version"
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.1'
    implementation 'androidx.activity:activity-compose:1.3.1'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"
    debugImplementation "androidx.compose.ui:ui-tooling:$compose_version"
    debugImplementation "androidx.compose.ui:ui-test-manifest:$compose_version"
 ## License
    Copyright (c) 2022 The Cats Team
    This project we create which the aim of education. 
