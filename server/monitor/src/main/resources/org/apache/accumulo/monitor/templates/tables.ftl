<#--

    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

-->
      <script>
        var tableList;
        /**
         * Creates DataTables table
         *   - uses ajax call for data source and saves sort state in session
         *   - defines custom number formats
         */
        $(document).ready(function () {

          tableList = $('#tableList').DataTable({
            "ajax": {
              "url": "/rest/tables",
              "dataSrc": "table"
            },
            "stateSave": true,
            "columnDefs": [
              {
                "type": "num",
                "targets": "big-num",
                "render": function (data, type, row) {
                  if (type === 'display')
                    data = bigNumberForQuantity(data);
                  return data;
                }
              },
              {
                "targets": "duration",
                "render": function (data, type, row) {
                  if (type === 'display') data = timeDuration(data);
                  return data;
                }
              },
              {
                "targets": [10],
                "type": "numeric",
                "orderData": [13, 14]
              },
              {
                "targets": [11],
                "type": "numeric",
                "orderData": [15, 16]
              },
              {
                "targets": [12],
                "type": "numeric",
                "orderData": [17, 18]
              }
            ],
            "columns": [
              {
                "data": "tablename",
                "type": "html",
                "render": function (data, type, row, meta) {
                  if (type === 'display') {
                    data = '<a href="/tables/' + row.tableId + '">' + row.tablename + '</a>';
                  }
                  return data;
                }
              },
              { "data": "tableState" },
              { "data": "tablets", "orderSequence": ["desc", "asc"] },
              { "data": "offlineTablets", "orderSequence": ["desc", "asc"] },
              { "data": "recs", "orderSequence": ["desc", "asc"] },
              { "data": "recsInMemory", "orderSequence": ["desc", "asc"] },
              { "data": "ingestRate", "orderSequence": ["desc", "asc"] },
              { "data": "entriesRead", "orderSequence": ["desc", "asc"] },
              { "data": "entriesReturned", "orderSequence": ["desc", "asc"] },
              { "data": "holdTime", "orderSequence": ["desc", "asc"] },
              { "data": "scansCombo", "orderSequence": ["desc", "asc"] },
              { "data": "minorCombo", "orderSequence": ["desc", "asc"] },
              { "data": "majorCombo", "orderSequence": ["desc", "asc"] },
              { "data": "runningScans", "orderSequence": ["desc", "asc"] },
              { "data": "queuedScans", "orderSequence": ["desc", "asc"] },
              { "data": "runningMinorCompactions", "orderSequence": ["desc", "asc"] },
              { "data": "queuedMinorCompactions", "orderSequence": ["desc", "asc"] },
              { "data": "runningMajorCompactions", "orderSequence": ["desc", "asc"] },
              { "data": "queuedMajorCompactions", "orderSequence": ["desc", "asc"] }
            ]
          });

            tableList.columns([13,14,15,16,17,18]).visible( false );
        });

        /**
         * Used to refresh the table
         */
        function refresh() {
          <#if js??>
            refreshManagerTables();
          </#if>

          tableList.ajax.reload(null, false); // user paging is not reset on reload
        }
      </script>
      <div class="row">
        <div class="col-xs-12">
          <h3>Table Overview</h3>
        </div>
      </div>
      <div>
        <table id="tableList" class="table caption-top table-bordered table-striped table-condensed">
          <caption><span class="table-caption">${tablesTitle}</span><br />
          <thead>
            <tr>
              <th>Table&nbsp;Name</th>
              <th>State</th>
              <th title="Tables are broken down into ranges of rows called tablets." class="big-num">Tablets</th>
              <th title="Tablets unavailable for query or ingest. May be a transient condition when tablets are moved for balancing." class="big-num">Offline</th>
              <th title="Key/value pairs over each instance, table or tablet." class="big-num">Entries</th>
              <th title="The total number of key/value pairs stored in memory and not yet written to disk." class="big-num">In&nbsp;Mem</th>
              <th title="The rate of Key/Value pairs inserted. (Note that deletes are considered inserted)" class="big-num">Ingest</th>
              <th title="The rate of Key/Value pairs read on the server side. Not all key values read may be returned to client because of filtering." class="big-num">Read</th>
              <th title="The rate of Key/Value pairs returned to clients during queries. This is not the number of scans." class="big-num">Returned</th>
              <th title="The amount of time live ingest operations (mutations, batch writes) have been waiting for the tserver to free up memory." class="duration">Hold&nbsp;Time</th>
              <th title="Running scans. The number queued waiting are in parentheses.">Scans</th>
              <th title="Minor Compactions. The number of tablets waiting for compaction are in parentheses.">MinC</th>
              <th title="Major Compactions. The number of tablets waiting for compaction are in parentheses.">MajC</th>
              <th title="Running Scans.">Running Scans</th>
              <th title="Queued Scans.">Queued Scans</th>
              <th title="Running MinC.">Running MinC</th>
              <th title="Queued MinC.">Queued MinC</th>
              <th title="Running MajC.">Running MajC</th>
              <th title="Queued MajC.">Queued MajC</th>
            </tr>
          </thead>
          <tbody></tbody>
        </table>
      </div>
