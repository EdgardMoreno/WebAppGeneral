editar documento con primefaces

 <h:column>                                
    <!--<p:commandLink action="#{productController.deleteAction(item)}"  update="form" >
        <span class="glyphicon glyphicon-trash" data-toggle="tooltip" title="Eliminar"></span>
        <p:confirm header="Confirmation" message="¿Está seguro de continuar?" icon="ui-icon-alert"  />
    </p:commandLink>-->
    &nbsp;
    <p:commandLink action="#{productController.editAction(productController.listProducts.indexOf(item) + 1)}" oncomplete="fnChangeTab(2)" update="form">
        <span class="glyphicon glyphicon-pencil" data-toggle="tooltip" title="Editar"></span>
    </p:commandLink>

</h:column>