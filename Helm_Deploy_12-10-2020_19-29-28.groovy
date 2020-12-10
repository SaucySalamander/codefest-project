
procedure 'Helm Deploy', {
  description = 'This will deploy our application'
  jobNameTemplate = ''
  projectName = 'Codefest'
  resourceName = 'codefest-agent-0'
  timeLimit = ''
  timeLimitUnits = 'minutes'
  workspaceName = ''

  formalParameter 'authorName', defaultValue: '', {
    description = ''
    expansionDeferred = '0'
    label = null
    orderIndex = null
    required = '1'
    type = 'entry'
  }

  formalParameter 'chartName', defaultValue: '', {
    description = 'Name of the chart we want to deploy'
    expansionDeferred = '0'
    label = null
    orderIndex = null
    required = '1'
    type = 'entry'
  }

  formalParameter 'namespace', defaultValue: '', {
    description = 'name of the namespace we want to deploy to'
    expansionDeferred = '0'
    label = null
    orderIndex = null
    required = '1'
    type = 'entry'
  }

  formalParameter 'releaseName', defaultValue: '', {
    description = 'release we want for a given chart'
    expansionDeferred = '0'
    label = null
    orderIndex = null
    required = '1'
    type = 'entry'
  }

  formalParameter 'repoName', defaultValue: '', {
    description = 'name of the helm repository'
    expansionDeferred = '0'
    label = null
    orderIndex = null
    required = '1'
    type = 'entry'
  }

  formalParameter 'repoUrl', defaultValue: '', {
    description = 'url for the helm repository'
    expansionDeferred = '0'
    label = null
    orderIndex = null
    required = '1'
    type = 'entry'
  }

  formalParameter 'valuesFile', defaultValue: '', {
    description = 'helm values file'
    expansionDeferred = '0'
    label = null
    orderIndex = null
    required = '0'
    type = 'entry'
  }

  formalParameter 'webhookUrl', defaultValue: '', {
    description = 'url to send teams webhook'
    expansionDeferred = '0'
    label = null
    orderIndex = null
    required = '1'
    type = 'entry'
  }

  step 'Initial Teams Notification', {
    description = ''
    alwaysRun = '0'
    broadcast = '0'
    command = '''url=\"$[webhookUrl]\"

curl -H \'Content-Type: application/json\' $url \\\n--data-binary @- << EOF 
{
    \"@type\": \"MessageCard\",
    \"@context\": \"http://schema.org/extensions\",
    \"themeColor\": \"0076D7\",
    \"summary\": \"New Pipeline Run\",
    \"sections\": [{
        \"activityTitle\": \"![TestImage](https://plugins.jetbrains.com/files/14675/93970/icon/pluginIcon.png)$[authorName] started a new pipeline run\",
        \"activitySubtitle\": \"Cloudbees CD\",
        \"activityImage\": \"https://plugins.jetbrains.com/files/14675/93970/icon/pluginIcon.png\",
        \"facts\": [{
        \"name\": \"Pipeline Id\",
        \"value\": \"$[/myPipelineRuntime/flowRuntimeId]\"
        },{
            \"name\": \"Status\",
            \"value\": \"In Progress\"
        }, {
            \"name\": \"Start Time\",
            \"value\": \"$(curl -s \"http://worldtimeapi.org/api/timezone/America/Chicago\" |awk -F, \'{print $3}\'|  sed -e \'s/\\\"//g\' | sed -e \'s/datetime://g\' | sed -e \'s/T/ /g\' | awk -F\\. \'{print $1}\')\"
        }],
        \"markdown\": true
    }],
    \"potentialAction\": [{
        \"@type\": \"OpenUri\",
        \"name\": \"Pipeline Run\",
        \"targets\": [
           { \"os\": \"default\", \"uri\": \"http://sdp.codefest.win/flow/#pipeline-run/$[/myPipeline/id]/$[/myPipelineRuntime/flowRuntimeId]\" }
        ]
    }]
}
EOF '''
    condition = ''
    errorHandling = 'failProcedure'
    exclusiveMode = 'none'
    logFileName = ''
    parallel = '0'
    postProcessor = ''
    precondition = ''
    projectName = 'Codefest'
    releaseMode = 'none'
    resourceName = 'codefest-agent-0'
    shell = ''
    subprocedure = null
    subproject = null
    timeLimit = ''
    timeLimitUnits = 'minutes'
    workingDirectory = ''
    workspaceName = ''
  }

  step 'Helm Install', {
    description = ''
    alwaysRun = '0'
    broadcast = '0'
    command = '''KUBECONFIG=\"/home/cbflow/.kube/config.yaml\"

helm repo add $[repoName] $[repoUrl]

helm repo update

helm upgrade --install $[chartName] $[repoName]/$[chartName] -n $[namespace] --timeout 10000s'''
    condition = ''
    errorHandling = 'abortProcedureNow'
    exclusiveMode = 'none'
    logFileName = ''
    parallel = '0'
    postProcessor = ''
    precondition = ''
    projectName = 'Codefest'
    releaseMode = 'none'
    resourceName = 'codefest-agent-0'
    shell = ''
    subprocedure = null
    subproject = null
    timeLimit = ''
    timeLimitUnits = 'minutes'
    workingDirectory = ''
    workspaceName = ''
  }

  step 'Status Teams Notification', {
    description = ''
    alwaysRun = '0'
    broadcast = '0'
    command = '''url=\"$[webhookUrl]\"

curl -H \'Content-Type: application/json\' $url \\\n--data-binary @- << EOF 
{
    \"@type\": \"MessageCard\",
    \"@context\": \"http://schema.org/extensions\",
    \"themeColor\": \"0076D7\",
    \"summary\": \"Pipeline Run\",
    \"sections\": [{
        \"activityTitle\": \"![TestImage](https://plugins.jetbrains.com/files/14675/93970/icon/pluginIcon.png)Status update on $[authorName]\'s pipeline run\",
        \"activitySubtitle\": \"Cloudbees CD\",
        \"activityImage\": \"https://plugins.jetbrains.com/files/14675/93970/icon/pluginIcon.png\",
        \"facts\": [{
        \"name\": \"Pipeline Id\",
        \"value\": \"$[/myPipelineRuntime/flowRuntimeId]\"
        },{
            \"name\": \"Status\",
            \"value\": \"$[/myStageRuntime/outcome]\"
        }, {
            \"name\": \"End Time\",
            \"value\": \"$(curl -s \"http://worldtimeapi.org/api/timezone/America/Chicago\" |awk -F, \'{print $3}\'|  sed -e \'s/\\\"//g\' | sed -e \'s/datetime://g\' | sed -e \'s/T/ /g\' | awk -F\\. \'{print $1}\')\"
        }],
        \"markdown\": true
    }],
    \"potentialAction\": [{
        \"@type\": \"OpenUri\",
        \"name\": \"Pipeline Run\",
        \"targets\": [
           { \"os\": \"default\", \"uri\": \"http://sdp.codefest.win/flow/#pipeline-run/$[/myPipeline/id]/$[/myPipelineRuntime/flowRuntimeId]\" }
        ]
    }]
}
EOF '''
    condition = ''
    errorHandling = 'failProcedure'
    exclusiveMode = 'none'
    logFileName = ''
    parallel = '0'
    postProcessor = ''
    precondition = ''
    projectName = 'Codefest'
    releaseMode = 'none'
    resourceName = 'codefest-agent-0'
    shell = ''
    subprocedure = null
    subproject = null
    timeLimit = ''
    timeLimitUnits = 'minutes'
    workingDirectory = ''
    workspaceName = ''
  }

  // Custom properties

  property 'ec_customEditorData', {

    // Custom properties

    property 'parameters', {

      // Custom properties

      property 'authorName', {

        // Custom properties
        formType = 'standard'
      }

      property 'chart', {

        // Custom properties
        formType = 'standard'
      }

      property 'chartName', {

        // Custom properties
        formType = 'standard'
      }

      property 'cluster', {

        // Custom properties
        formType = 'standard'
      }

      property 'namespace', {

        // Custom properties
        formType = 'standard'
      }

      property 'release-name', {

        // Custom properties
        formType = 'standard'
      }

      property 'releaseName', {

        // Custom properties
        formType = 'standard'
      }

      property 'repo', {

        // Custom properties
        formType = 'standard'
      }

      property 'repo-name', {

        // Custom properties
        formType = 'standard'
      }

      property 'repoName', {

        // Custom properties
        formType = 'standard'
      }

      property 'repoUrl', {

        // Custom properties
        formType = 'standard'
      }

      property 'teams-url', {

        // Custom properties
        formType = 'standard'
      }

      property 'values-file', {

        // Custom properties
        formType = 'standard'
      }

      property 'valuesFile', {

        // Custom properties
        formType = 'standard'
      }

      property 'webhookUrl', {

        // Custom properties
        formType = 'standard'
      }
    }
  }
}
