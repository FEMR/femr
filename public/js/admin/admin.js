var manageUpdateDB = {
    elements: {
        adminUpdateDBButton: $('.adminUpdateDBButton')
    },
    bindAdminUpdateDBButtons: function () {
        manageUpdateDB.elements.adminUpdateDBButton.click(function () {
            manageUpdateDB.updateDB(this);
        });
    },
    updateDB: function(btn) {
        $.ajax({
            url: '/admin/updateDb',
            type: 'POST',
            data: {},
            dataType: 'text',
            success: function (isDeleted) {
                //TODO: signal the user that the update was successful
            },
            error: function () {
                //TODO: notify user on error
            }
        });
    }
}