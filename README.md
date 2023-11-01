# Team 10 App


# How to run
Put something like this:
```json
{
    // Use IntelliSense to learn about possible attributes.
    // Hover to view descriptions of existing attributes.
    // For more information, visit: https://go.microsoft.com/fwlink/?linkid=830387
    "version": "0.2.0",
    "configurations": [
       {
            "type": "java",
            "name": "App",
            "request": "launch",
            "mainClass": "App.Main",
            "projectName": "cse-110-project-team-10_71909f5d",
            "vmArgs": "--module-path 'C:\\Users\\camer\\Documents\\openjfx-21_windows-x64_bin-sdk\\javafx-sdk-21\\lib' --add-modules javafx.controls,javafx.fxml"
        }
    ]
}
```

In your .vscode/launch.json file, but replace that hardcoded path with wherever your openjfx download is. Projectname might also be wrong, but hopefully not!