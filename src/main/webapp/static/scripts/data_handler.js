export function getApi(url, callback) {
    fetch(url, {
        method: 'GET',
        credentials: "same-origin",
    })
        .then(response => response.json())
        .then(json_response => callback(json_response))
    ;
}

export function postApi(url, data, callback) {
    fetch(url, {
      method: "POST",
      headers: { 'Accept': 'application/json',
          'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    })
      .then(response => response.json())
        .then(data => callback(data))
    ;
}