# Twitter Example

A Twitter bot example in Clojure

## Step-by-step Tutorial

1. Make sure you have followed the [Lemmings Clojure and Atom Editor setup](https://lemmings.io/clojure-and-atom-editor-setup-40f8f09237b4).

1. To get familiar with the programming language Clojure and the development workflow, first off follow the step-by-step tutorial [Twitter Bot Example in Clojure](http://howistart.org/posts/clojure/1). Skip the steps for setting up the Emacs Editor and testing in the tutorial.

2. Start a new vagrant session by opening a new terminal window and run `vagrant ssh`.

    ![vagrant ssh](doc/images/vagrant-ssh.png)

3. In the VM, change into the projects directory with `cd projects/` and start with the tutorial.

    ![cd projects](doc/images/cd-projects.png)

4. "Clone" the Twitter example into your Vagrant environment

    ```shell
    git clone https://github.com/lemmings-io/01-twitter-example.git
    ```

    Now change into the twitter example directory:

    ```shell
    cd 01-twitter-example
    ```

5. Start the nREPL server

    Start another VM session by opening a new terminal window and run `vagrant ssh`.

    Navigate to the project folder you just created with `cd projects/<your-project-name>`.

    Start the nREPL server with `lein repl :headless :host 0.0.0.0 :port 7888` as described in the [Clojure and Atom Editor Setup](https://lemmings.io/clojure-and-atom-editor-setup-40f8f09237b4)

   ![run nREPL server](doc/images/nrepl-server.png)

6. Connect to Atom's nREPL

   ![nrepl-connection-successful](doc/images/nrepl-connection-successful.png)


7. Provide your Twitter API tokens for local development by creating a file called `profiles.clj` in `01-twitter-example` with Atom:

    ```clojure
    {:dev {:env {:app-consumer-key "<REPLACE>"
                 :app-consumer-secret "<REPLACE>"
                 :user-access-token "<REPLACE>"
                 :user-access-secret "<REPLACE>"}}}
    ```
    ![profiles.clj](doc/images/profiles-clj.png)

3. Start the twitter bot inside Vagrant:

    In the Terminal where you are connected to Vagrant (`vagrant ssh`) run `lein trampoline run` to start the local server.

4. Test if your bot tweets in Atom's nREPL

    Run `(ns twitter-example.core)` to initialize your app's namespace in the nREPL.

    Then run `(status-update)` in the nREPL to send a status update through your twitter account.

    ![tweet via repl](doc/images/tweet-via-repl.png)
    ![twitter tweet](doc/images/twitter-tweet.png)


## Credit

This example is based on the [Twitter Bot Example in Clojure](http://howistart.org/posts/clojure/1) by [Carin Meier](https://twitter.com/carinmeier).

## License

Copyright © 2017

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
