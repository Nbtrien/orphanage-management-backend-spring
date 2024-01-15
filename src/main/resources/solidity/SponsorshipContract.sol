// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.0;

contract SponsorshipContract {
    // Struct để lưu trữ thông tin tài trợ
    struct Sponsorship {
        address sponsor;
        string information;
    }

    // Mảng để lưu trữ danh sách các thông tin tài trợ
    Sponsorship[] public sponsorships;

    // Sự kiện được kích hoạt khi có thông tin tài trợ mới
    event SponsorshipAdded(address indexed sponsor, string information);

    // Hàm để thêm thông tin tài trợ mới
    function addSponsorship(string memory _information) public {
        Sponsorship memory newSponsorship = Sponsorship({
            sponsor: msg.sender,
            information: _information
        });

        sponsorships.push(newSponsorship);

        // Kích hoạt sự kiện SponsorshipAdded
        emit SponsorshipAdded(msg.sender, _information);
    }

    // Hàm để lấy số lượng thông tin tài trợ
    function getSponsorshipCount() public view returns (uint) {
        return sponsorships.length;
    }

    // Hàm để lấy thông tin tài trợ theo index
    function getSponsorship(uint index) public view returns (address, string memory) {
        require(index < sponsorships.length, "Index out of bounds");

        Sponsorship storage sponsorship = sponsorships[index];
        return (sponsorship.sponsor, sponsorship.information);
    }
}