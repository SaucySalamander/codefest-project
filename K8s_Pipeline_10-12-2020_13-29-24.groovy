pipeline 'K8s_Pipeline', {
  description = ''
  disableMultipleActiveRuns = '0'
  disableRestart = '0'
  enabled = '1'
  overrideWorkspace = '0'
  projectName = 'Codefest'
  skipStageMode = 'ENABLED'

  formalParameter 'chartName', {
    expansionDeferred = '0'
    orderIndex = '1'
    required = '1'
    type = 'entry'
  }

  formalParameter 'namespace', {
    expansionDeferred = '0'
    orderIndex = '2'
    required = '1'
    type = 'entry'
  }

  formalParameter 'releaseName', {
    expansionDeferred = '0'
    orderIndex = '3'
    required = '1'
    type = 'entry'
  }

  formalParameter 'repoName', {
    expansionDeferred = '0'
    orderIndex = '4'
    required = '1'
    type = 'entry'
  }

  formalParameter 'repoUrl', {
    expansionDeferred = '0'
    orderIndex = '5'
    required = '1'
    type = 'entry'
  }

  formalParameter 'valuesFile', {
    expansionDeferred = '0'
    orderIndex = '6'
    required = '0'
    type = 'entry'
  }

  formalParameter 'webhookUrl', {
    expansionDeferred = '0'
    orderIndex = '7'
    required = '1'
    type = 'entry'
  }

  formalParameter 'authorName', {
    expansionDeferred = '0'
    orderIndex = '8'
    required = '1'
    type = 'entry'
  }

  formalParameter 'ec_stagesToRun', {
    expansionDeferred = '1'
    required = '0'
  }

  stage 'Deploy', {
    description = ''
    colorCode = '#00adee'
    completionType = 'auto'
    pipelineName = 'K8s_Pipeline'
    waitForPlannedStartDate = '0'

    gate 'PRE', {
      }

    gate 'POST', {
      }

    task 'Deploy', {
      description = ''
      actualParameter = [
        'authorName': '$[/myPipelineRuntime/authorName]',
        'chartName': '$[/myPipelineRuntime/chartName]',
        'namespace': '$[/myPipelineRuntime/namespace]',
        'releaseName': '$[/myPipelineRuntime/releaseName]',
        'repoName': '$[/myPipelineRuntime/repoName]',
        'repoUrl': '$[/myPipelineRuntime/repoUrl]',
        'valuesFile': '$[/myPipelineRuntime/valuesFile]',
        'webhookUrl': '$[/myPipelineRuntime/webhookUrl]',
      ]
      advancedMode = '0'
      allowOutOfOrderRun = '0'
      alwaysRun = '0'
      enabled = '1'
      errorHandling = 'stopOnError'
      insertRollingDeployManualStep = '0'
      resourceName = 'codefest-agent-0'
      skippable = '0'
      subprocedure = 'Helm Deploy'
      subproject = 'Codefest'
      taskType = 'PROCEDURE'
      useApproverAcl = '0'
      waitForPlannedStartDate = '0'
    }
  }

  // Custom properties

  property 'ec_counters', {

    // Custom properties
    pipelineCounter = '8'
  }
}