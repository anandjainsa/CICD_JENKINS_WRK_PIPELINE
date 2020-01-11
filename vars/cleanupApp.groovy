def call()
{
        //Roll out to Dev Environment
          sh '''
                cd /home/svc_mwadmin/ucp
                ls -ltr
                eval "$(cat env.sh)"
                kubectl get all --namespace=ns-middleware-dv1
                kubectl get ingress --namespace=ns-middleware-dv1
                kubectl delete deploy oncallservices --namespace=ns-middleware-dv1
                kubectl delete svc oncallservices --namespace=ns-middleware-dv1
                kubectl delete ingress oncallservices  --namespace=ns-middleware-dv1
                kubectl get ingress --namespace=ns-middleware-dv1
                kubectl get all --namespace=ns-middleware-dv1
		'''
}
