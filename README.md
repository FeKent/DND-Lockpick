# DND-Lockpick

This Android application allows users to engage in a virtual lockpicking experience. Users can customize the difficulty by choosing the number of tumblers in the lock and setting a time limit for successfully picking the lock. The game incorporates various features, including a pause timer and restart button.

---

## Features

- **Customizable Tumblers:** Users can choose the number of tumblers in the lock, making the game more challenging and entertaining.

- **Time Limit:** Set a time limit for successfully picking the lock. Test your lockpicking skills under pressure!

- **Pause Timer:** Need a break? Pause the timer to resume the game later. The pause feature adds flexibility to your lockpicking experience.

- **Restart Button:** Want to try again? Hit the restart button to reset the lock and timer, providing endless opportunities to improve your skills.

- **Alert Dialog:** A warning dialog pops up if the user attempts to go back to the landing screen while the timer is running, preventing accidental exits and maintaining a seamless gaming experience.

- **MVVM Design Pattern:** The application follows the Model-View-ViewModel (MVVM) design pattern, ensuring a clean and maintainable code structure.

- **Compose Navigation and ViewModels:** Utilizing Jetpack Compose for UI and navigation, along with ViewModels for managing UI-related data, the application offers a modern and efficient development approach.
  - Two distinct approaches are employed for state management within the ViewModels. In one ViewModel, the state is managed using coroutines and flows from the kotlinx library. In contrast, the other ViewModel utilizes Compose's native MutableStateOf for a concise and effective state management strategy. 

- **Vibration, Animation, and Media Player:** Enhance the gaming experience with vibration feedback, engaging animations, and background music using the media player.

---


![DND Lockpick Gif](https://github.com/FeKent/DND-Lockpick/assets/118063936/8f541cbd-02cf-4eab-bff5-10d15e3a0a45)
