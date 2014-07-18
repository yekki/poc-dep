
startEdit()

cd('/')
cmo.createJMSServer('de-jms-server')

cd('/JMSServers/de-jms-server')
set('Targets',jarray.array([ObjectName('com.bea:Name=DECenter,Type=Server')], ObjectName))

activate()

startEdit()

cd('/')
cmo.createJMSSystemResource('de-module')

cd('/JMSSystemResources/de-module')
set('Targets',jarray.array([ObjectName('com.bea:Name=DECenter,Type=Server')], ObjectName))

activate()

startEdit()

cd('/JMSSystemResources/de-module/JMSResource/de-module')
cmo.createConnectionFactory('de-cf')

cd('/JMSSystemResources/de-module/JMSResource/de-module/ConnectionFactories/de-cf')
cmo.setJNDIName('me.yekki.dep.connection_factory')

cd('/JMSSystemResources/de-module/JMSResource/de-module/ConnectionFactories/de-cf/SecurityParams/de-cf')
cmo.setAttachJMSXUserId(false)

cd('/JMSSystemResources/de-module/JMSResource/de-module/ConnectionFactories/de-cf/ClientParams/de-cf')
cmo.setClientIdPolicy('Restricted')
cmo.setSubscriptionSharingPolicy('Exclusive')
cmo.setMessagesMaximum(10)

cd('/JMSSystemResources/de-module/JMSResource/de-module/ConnectionFactories/de-cf/TransactionParams/de-cf')
cmo.setXAConnectionFactoryEnabled(true)

cd('/JMSSystemResources/de-module/JMSResource/de-module/ConnectionFactories/de-cf')
cmo.setDefaultTargetingEnabled(true)

activate()

startEdit()

cd('/JMSSystemResources/de-module')
cmo.createSubDeployment('de-queue')

startEdit()

cd('/JMSSystemResources/de-module/JMSResource/de-module')
cmo.createQueue('de-inbound')

cd('/JMSSystemResources/de-module/JMSResource/de-module/Queues/de-inbound')
cmo.setJNDIName('me.yekki.dep.inbound')
cmo.setSubDeploymentName('de-queue')

cd('/JMSSystemResources/de-module/SubDeployments/de-queue')
set('Targets',jarray.array([ObjectName('com.bea:Name=de-jms-server,Type=JMSServer')], ObjectName))

activate()

startEdit()

cd('/JMSSystemResources/de-module/JMSResource/de-module')
cmo.createQueue('de-queue1')

cd('/JMSSystemResources/de-module/JMSResource/de-module/Queues/de-queue1')
cmo.setJNDIName('me.yekki.dep.outbound.1')
cmo.setSubDeploymentName('de-queue')

cd('/JMSSystemResources/de-module/SubDeployments/de-queue')
set('Targets',jarray.array([ObjectName('com.bea:Name=de-jms-server,Type=JMSServer')], ObjectName))

activate()

startEdit()

cd('/JMSSystemResources/de-module/JMSResource/de-module')
cmo.createQueue('de-queue2')

cd('/JMSSystemResources/de-module/JMSResource/de-module/Queues/de-queue2')
cmo.setJNDIName('me.yekki.dep.outbound.2')
cmo.setSubDeploymentName('de-queue')

cd('/JMSSystemResources/de-module/SubDeployments/de-queue')
set('Targets',jarray.array([ObjectName('com.bea:Name=de-jms-server,Type=JMSServer')], ObjectName))

activate()

startEdit()
