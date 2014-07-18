#Install Guide

1. create two domains which named:de-center & de-fronter, make sure their admin server name is unique, for example:DECenter, DEFronter
2. create jms resources on de-center via wlst script:*create_jms_res.py*
3. enable trust-domain on all domains. wls console navigation:[domain name]->Security->Advanced->Credential, make sure all domains use same credential.
4. build & generate components which named: dispatcher.jar, listners.jar, timer.jar
4. deploy dispater.jar on de-center
5. deploy listeners.jar & timer.jar on de-fronter

#How works

1. timer.jar generate jms messages(with policy header named 'router', the value is random:dest1, dest2 or all) every 5 sec and send it to queue me.yekki.de.inbound on de-center.
2. dispatcher.jar received the messages sent from timer.jar and dispatch the messages to different queues based on policy.
3. listeners.jar has two MDBs which listen on different queues. When got messages from dispatcher, the MDBs will be invoked and print out log info for demo purpose.


#Highlight Points

1. It's purely based Java EE6 technology which means it's a open standard data exachange platform.
2. Every layers can be horizontal scalability based on application server cluster capability.