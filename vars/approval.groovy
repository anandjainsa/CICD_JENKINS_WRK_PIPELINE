def call() {
  stage 'Manual Promotion'
    // we need a first milestone step so that all jobs entering this stage are tracked an can be aborted if needed
    milestone 1
    // time out manual approval after ten minutes
    timeout(time: 10, unit: 'MINUTES') {
        input message: "Does Dev look good? "
    }
    // this will kill any job which is still in the input step
    milestone 2
}
