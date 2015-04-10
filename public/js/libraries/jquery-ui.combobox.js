/**
 * Created by owner1 on 4/10/2015.
 */

(function( $ ) {
    $.widget( "custom.combobox", {
        _create: function() {
            var self = this;
            this.wrapper = $( "<span></span>" )
                .addClass( "custom-combobox" )
                .insertAfter( this.element );
            this.element.hide();
            this._createAutocomplete();
            this._createShowAllButton();

            this.input.data("ui-autocomplete")._renderItem = function(ul, item) {
                return $("<li></li>").data("item.autocomplete", item).append("<a>" + item.label + "</a>").appendTo(ul);
            };
        },

        _createAutocomplete: function() {
            var value = "";

            var source = this._source;

            /* Allow overriding source */
            if (typeof this.options.source === "function") source = this.options.source;

            this.input = $( "<input>" )
                .appendTo( this.wrapper )
                .val( value )
                .attr( "title", "" )
                .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
                .autocomplete({
                    delay: 0,
                    minLength: 0,
                    source: source,
                    select: function(event, ui) {}
                })
                .tooltip({
                    tooltipClass: "ui-state-highlight"
                });

            this._on( this.input, {
                autocompleteselect: function( event, ui ) {
                    this._trigger( "select", event, {
                        item: ui.item
                    });
                },
                autocompletechange: "_removeIfInvalid"
            });
        },

        _createShowAllButton: function() {
            var input = this.input,
                wasOpen = false;

            $( "<a></a>" )
                .attr( "tabIndex", -1 )
                .attr( "title", "Show All Items" )
                .tooltip()
                .appendTo( this.wrapper )
                .button({
                    icons: {
                        primary: "ui-icon-triangle-1-s"
                    },
                    text: false
                })
                .removeClass( "ui-corner-all" )
                .addClass( "custom-combobox-toggle ui-corner-right" )
                .mousedown(function() {
                    wasOpen = input.autocomplete( "widget" ).is( ":visible" );
                })
                .click(function() {
                    input.focus();

                    // Close if already visible
                    if ( wasOpen ) {
                        return;
                    }

                    // Pass empty string as value to search for, displaying all results
                    input.autocomplete( "search", "" );
                });
        },
        _source: function(request, response) {
            // Autocomplete source
        },
        _removeIfInvalid: function( event, ui ) {
            // Selected an item, nothing to do
            if ( ui.item ) {
                return;
            }

            if (!ui.item) {
                // Search for a match (case-insensitive)
                var value = this.input.val(),
                    valueLowerCase = value.toLowerCase(),
                    valid = false;

                var autocomplete = this.input.data("ui-autocomplete");
                autocomplete.widget().children(".ui-menu-item").each(function () {
                    var item = $(this).data("item.autocomplete");
                    if (item.name.toLowerCase() == valueLowerCase) {
                        ui.item = item;
                        valid = true;
                        return false;
                    }
                });
            }
            // Found a match, nothing to do
            if ( ui.item ) {
                this.input.val(ui.item.label);
                this._trigger( "select", event, {
                    item: ui.item
                });
                return;
            }

            // Remove invalid value
            this.input
                .val( "" )
                .attr( "title", value + " didn't match any item" )
                .tooltip( "open" );
            this.element.val( "" );
            this._delay(function() {
                this.input.tooltip( "close" ).attr( "title", "" );
            }, 2500 );
        },

        _destroy: function() {
            this.wrapper.remove();
            this.element.show();
        }
    });
})( jQuery );